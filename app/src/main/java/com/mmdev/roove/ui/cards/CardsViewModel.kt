/*
 * Created by Andrii Kovalchuk
 * Copyright (c) 2019. All rights reserved.
 * Last modified 09.12.19 20:46
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mmdev.roove.ui.cards

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmdev.business.cards.entity.CardItem
import com.mmdev.business.cards.usecase.AddToSkippedUseCase
import com.mmdev.business.cards.usecase.CheckMatchUseCase
import com.mmdev.business.cards.usecase.GetUsersByPreferencesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CardsViewModel @Inject constructor(private val addToSkippedUC: AddToSkippedUseCase,
                                         private val checkMatchUC: CheckMatchUseCase,
                                         private val getUsersByPreferencesUC: GetUsersByPreferencesUseCase):
		ViewModel(){

	private val usersCardsList: MutableLiveData<List<CardItem>> = MutableLiveData()

	val showLoading: MutableLiveData<Boolean> = MutableLiveData()
	val showMatchDialog: MutableLiveData<Boolean> = MutableLiveData()
	val showTextHelper: MutableLiveData<Boolean> = MutableLiveData()


	private val disposables = CompositeDisposable()

	companion object {
		private const val TAG = "mylogs"
	}


	fun addToSkipped(skippedCardItem: CardItem) {
		addToSkippedExecution(skippedCardItem)
		Log.wtf(TAG, "skipped + ${skippedCardItem.name}")
	}


	fun checkMatch(likedCardItem: CardItem){
		disposables.add(checkMatchExecution(likedCardItem)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                           showMatchDialog.value = it
                           Log.wtf(TAG, "its a match! + ${likedCardItem.name}")
                       },
                       {
                           Log.wtf(TAG, "error swiped + $it")
                       }))
	}


	fun loadUsersByPreferences(){
		disposables.add(getUsersByPreferencesExecution()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading.value = true }
            .doOnSuccess {
                if(it.isNotEmpty()) showLoading.value = false
                else showTextHelper.value = true
            }
            .subscribe({
	                       Log.wtf(TAG, "cards to show: ${it.size}")
	                       usersCardsList.value = it
                       },
                       {
	                       Log.wtf(TAG, "get potential users error: $it")
                       }))
	}


	fun getUsersCardsList() = usersCardsList



	private fun addToSkippedExecution(skippedCardItem: CardItem) = addToSkippedUC.execute(skippedCardItem)

	private fun checkMatchExecution(likedCardItem: CardItem) = checkMatchUC.execute(likedCardItem)

	private fun getUsersByPreferencesExecution() = getUsersByPreferencesUC.execute()



	override fun onCleared() {
		disposables.clear()
		super.onCleared()
	}
}

