@file:OptIn(ExperimentalCoroutinesApi::class)

package cz.lastaapps.api.login

import androidx.test.filters.SmallTest
import cz.lastaapps.api.login.entities.School
import cz.lastaapps.api.login.entities.Town
import cz.lastaapps.api.login.school.SchoolRequestsImpl
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotHaveSize
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@SmallTest
internal class SchoolRequestsImplTest {

    @Test
    fun loadTowns() = runTest {
        val towns = SchoolRequestsImpl().loadTowns()
        towns shouldNotBe null
        towns!! shouldNotHaveSize 0
        towns shouldContain Town("Pelhřimov", 7)
    }

    @Test
    fun loadSchools() = runTest {
        val schools = SchoolRequestsImpl().loadSchools(Town("Pelhřimov", 7))
        schools shouldNotBe null
        schools!! shouldNotHaveSize 0
        schools shouldContain School(
            "SYDATAABIH",
            "Gymnázium Pelhřimov, Jirsíkova 244",
            "https://bakalari.gyoa.cz/"
        )
    }
}