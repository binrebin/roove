package com.mmdev.business.cards.usecase

import com.mmdev.business.cards.repository.CardsRepository
import com.mmdev.business.user.model.User

/* Created by A on 17.09.2019.*/

/**
 * This is the documentation block about the class
 */

class AddToSkippedUseCase (private val repository: CardsRepository)  {

	fun execute(t: User) = repository.addToSkipped(t)

}