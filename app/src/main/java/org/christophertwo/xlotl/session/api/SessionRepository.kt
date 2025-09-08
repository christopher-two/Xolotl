package org.christophertwo.xlotl.session.api

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    /**
     * Observa el estado de autenticación del usuario.
     * @return Un Flow que emite true si hay un UID de usuario guardado, false en caso contrario.
     */
    fun isUserLoggedIn(): Flow<Boolean>

    /**
     * Guarda el UID del usuario, marcándolo como logueado.
     * @param uid El UID del usuario a guardar.
     * @return Un Result que indica el éxito o fracaso de la operación.
     */
    suspend fun saveUserSession(uid: String): Result<Unit>

    /**
     * Limpia el UID del usuario guardado, cerrando la sesión.
     * @return Un Result que indica el éxito o fracaso de la operación.
     */
    suspend fun clearUserSession(): Result<Unit>

    /**
     * Obtiene el UID del usuario actualmente logueado.
     * @return Un Flow que emite el UID del usuario si está logueado, o null en caso contrario.
     */
    fun getCurrentUserId(): Flow<String?>
}