package cz.lastaapps.api.login

import cz.lastaapps.api.login.entities.LoginResponse
import cz.lastaapps.api.login.entities.SchoolsInTown
import cz.lastaapps.api.login.entities.Town
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

class SerializationTest {

    @Test
    fun testTown() {
        val data = """
        [
          {
            "name": "",
            "schoolCount": 2
          },
          {
            "name": "Albrechtice",
            "schoolCount": 1
          },
          {
            "name": "Aš",
            "schoolCount": 3
          }
        ]    
        """.trimIndent()
        Json.decodeFromString<List<Town>>(data)
    }

    @Test
    fun testSchool() {
        val data = """
           {
             "name": "beroun",
             "schools": [
               {
                 "id": "SYHKTAAAAB",
                 "name": "Mateřská škola Montessori Beroun a Základní škola s.r.o.",
                 "schoolUrl": "https://montessori-beroun.bakalari.cz"
               },
               {
                 "id": "SYDATAAABA",
                 "name": "Základní škola, Beroun - Závodí, Komenského 249",
                 "schoolUrl": "https://zavodi.bakalari.cz"
               },
               {
                 "id": "SYDATAADKO",
                 "name": "Manažerská akademie, soukromá střední škola",
                 "schoolUrl": "https://maberoun.bakalari.cz/"
               }
             ]
           } 
        """.trimIndent()
        Json.decodeFromString<SchoolsInTown>(data)
    }

    @Test
    fun testLogin() {
        val data = """
        {
          "bak:ApiVersion": "3.19.0",
          "bak:AppVersion": "1.48.422.1",
          "bak:UserId": "USERID",
          "access_token": "token",
          "token_type": "Bearer",
          "expires_in": 3599,
          "scope": "scopes",
          "refresh_token": "token"
        } 
        """.trimIndent()
        Json.decodeFromString<LoginResponse>(data)
    }

}