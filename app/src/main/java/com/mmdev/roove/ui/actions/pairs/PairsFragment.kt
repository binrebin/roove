/*
 * Created by Andrii Kovalchuk on 25.11.19 20:00
 * Copyright (c) 2019. All rights reserved.
 * Last modified 25.11.19 20:00
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mmdev.roove.ui.actions.pairs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmdev.roove.R
import com.mmdev.roove.core.injector
import com.mmdev.roove.ui.main.view.MainActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * This is the documentation block about the class
 */

class PairsFragment: Fragment(R.layout.fragment_pairs) {


	private lateinit var mMainActivity: MainActivity

	private val mPairsAdapter: PairsAdapter = PairsAdapter(listOf())

	//for potential
	private lateinit var pairsViewModel: PairsViewModel
	private val factory = injector.factory()

	private val disposables = CompositeDisposable()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activity?.let { mMainActivity = it as MainActivity }

		pairsViewModel = ViewModelProvider(this, factory).get(PairsViewModel::class.java)
		pairsViewModel.loadMatchedUsers()

		//get matched users
		pairsViewModel.getMatchedUsers().observe(this, Observer { mPairsAdapter.updateData(it) })
	}


	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val rvPairsList = view.findViewById<RecyclerView>(R.id.pairs_container_rv)
		rvPairsList.apply {
			adapter = mPairsAdapter
			layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
			itemAnimator = DefaultItemAnimator()
		}

		mPairsAdapter.setOnItemClickListener(object: PairsAdapter.OnItemClickListener {
			override fun onItemClick(view: View, position: Int) {
				val pairItem = mPairsAdapter.getPairItem(position)

				mMainActivity.partnerId = pairItem.userId
				mMainActivity.partnerMainPhotoUrl = pairItem.mainPhotoUrl
				mMainActivity.partnerName = pairItem.name

				mMainActivity.cardItemClicked = pairItem

				mMainActivity.startProfileFragment(pairItem.userId, true)


			}
		})
	}



	override fun onDestroy() {
		super.onDestroy()
		disposables.clear()
	}


}