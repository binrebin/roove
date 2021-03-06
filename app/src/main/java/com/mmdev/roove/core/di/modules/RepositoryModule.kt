/*
 * Created by Andrii Kovalchuk
 * Copyright (c) 2019. All rights reserved.
 * Last modified 04.12.19 19:13
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.mmdev.roove.core.di.modules

import com.mmdev.business.auth.repository.AuthRepository
import com.mmdev.business.cards.repository.CardsRepository
import com.mmdev.business.chat.repository.ChatRepository
import com.mmdev.business.conversations.repository.ConversationsRepository
import com.mmdev.business.events.repository.EventsRepository
import com.mmdev.business.feed.repository.FeedRepository
import com.mmdev.business.pairs.PairsRepository
import com.mmdev.business.user.repository.UserRepository
import com.mmdev.data.auth.AuthRepositoryImpl
import com.mmdev.data.cards.CardsRepositoryImpl
import com.mmdev.data.chat.ChatRepositoryImpl
import com.mmdev.data.conversations.ConversationsRepositoryImpl
import com.mmdev.data.events.EventsRepositoryImpl
import com.mmdev.data.feed.FeedRepositoryImpl
import com.mmdev.data.pairs.PairsRepositoryImpl
import com.mmdev.data.user.UserRepositoryLocal
import com.mmdev.data.user.UserRepositoryRemote
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

	@Provides
	@Singleton
	fun authRepository(repository: AuthRepositoryImpl): AuthRepository { return repository }

	@Provides
	@Singleton
	fun cardsRepository(repository: CardsRepositoryImpl): CardsRepository { return repository }

	@Provides
	@Singleton
	fun chatRepository(repository: ChatRepositoryImpl): ChatRepository { return repository }

	@Provides
	@Singleton
	fun conversationsRepository(repository: ConversationsRepositoryImpl): ConversationsRepository
	{ return repository }

	@Provides
	@Singleton
	fun eventsRepository(repository: EventsRepositoryImpl): EventsRepository { return repository }

	@Provides
	@Singleton
	fun feedRepository(repository: FeedRepositoryImpl): FeedRepository { return repository }

	@Provides
	@Singleton
	fun pairsRepository(repository: PairsRepositoryImpl): PairsRepository { return repository }

	@Provides
	@Singleton
	fun localUserRepository(repository: UserRepositoryLocal): UserRepository.LocalUserRepository
	{ return repository }

	@Provides
	@Singleton
	fun remoteUserRepository(repository: UserRepositoryRemote): UserRepository.RemoteUserRepository
	{ return repository }

}
