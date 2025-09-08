package org.christophertwo.xlotl

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import org.christophertwo.xlotl.auth.api.GoogleAuthManager
import org.christophertwo.xlotl.auth.impl.google.GoogleAuthManagerImpl
import org.christophertwo.xlotl.core.DiQualifiers
import org.christophertwo.xlotl.domain.usecase.SignInWithGoogleUseCase
import org.christophertwo.xlotl.presentation.screens.start.StartViewModel
import org.christophertwo.xlotl.session.api.SessionRepository
import org.christophertwo.xlotl.session.impl.datastore.DataStoreSessionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

class Application : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                getDataModules() + getDomainModules() + getPresentationModules()
            )
        }
    }

    fun getDataModules() = listOf(
        authModule, dataModule
    )

    fun getDomainModules() = listOf(domainModule)
    fun getPresentationModules() = listOf(viewmodelModule)

    val viewmodelModule: Module
        get() = module {
            viewModelOf(::StartViewModel)
        }

    val authModule: Module
        get() = module {
            singleOf(::GoogleAuthManagerImpl).bind(GoogleAuthManager::class)
        }

    val domainModule: Module
        get() = module {
            singleOf(::SignInWithGoogleUseCase)
        }

    val dataModule: Module
        get() = module {
            single(qualifier = DiQualifiers.SESSION_DATASTORE) {
                androidContext().sessionDataStore
            }
            single(qualifier = DiQualifiers.SETTINGS_DATASTORE) {
                androidContext().settingsDataStore
            }
            single<SessionRepository> {
                DataStoreSessionRepository(get(qualifier = DiQualifiers.SESSION_DATASTORE))
            }
            single<FirebaseAuth> { FirebaseAuth.getInstance() }
        }
}