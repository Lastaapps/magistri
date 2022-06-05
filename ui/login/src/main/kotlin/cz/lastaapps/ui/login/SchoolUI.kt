package cz.lastaapps.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cz.lastaapps.api.login.entities.School
import cz.lastaapps.api.login.entities.Town
import cz.lastaapps.ui.common.UsableDialog
import java.text.Normalizer

@Composable
fun SchoolUI(
    schoolsViewModel: SchoolsViewModel,
    url: String, onUrlChanged: (String) -> Unit,
    enabled: Boolean, modifier: Modifier = Modifier,
) {
    with(schoolsViewModel) {
        SchoolUI(
            selectedTown.collectAsState().value,
            townList.collectAsState().value, this::selectTown,
            townLoading.collectAsState().value, this::reloadTowns,

            selectedSchool.collectAsState().value,
            schoolList.collectAsState().value, this::selectSchool,
            schoolLoading.collectAsState().value, this::reloadSchools,

            url, onUrlChanged,
            enabled, modifier,
        )
    }
}

@Composable
private fun SchoolUI(
    town: Town?, townList: List<Town>, onTownSelected: (Town) -> Unit,
    townLoading: Boolean, onRefreshTown: () -> Unit,
    school: School?, schoolList: List<School>, onSchoolSelected: (School) -> Unit,
    schoolLoading: Boolean, onRefreshSchool: () -> Unit,
    url: String, onUrlChanged: (String) -> Unit,
    enabled: Boolean, modifier: Modifier = Modifier,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            Modifier
                .animateContentSize()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SelectedTown(town, townList, onTownSelected, enabled)
            if (town != null)
                SelectedSchool(
                    school,
                    schoolList,
                    onSchoolSelected,
                    enabled,
                )
            UrlField(url, onUrlChanged, enabled, Modifier.fillMaxWidth())
        }
        if (town == null) {
            LoadingBox(townList, townLoading, onRefreshTown)
        } else if (school == null) {
            LoadingBox(schoolList, schoolLoading, onRefreshSchool)
        }
    }
}

@Composable
private fun SelectedTown(
    town: Town?, townList: List<Town>, onTownSelected: (Town) -> Unit,
    enabled: Boolean, modifier: Modifier = Modifier,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    SelectedSomething(
        Icons.Default.LocationCity,
        town?.name ?: "No town selected",
        { showDialog = true },
        enabled,
        modifier,
    )

    SelectDialog(
        showDialog,
        townList, onTownSelected,
        "Select town", { it.nameWithNumber }
    ) { showDialog = false }
}

@Composable
private fun SelectedSchool(
    school: School?, schoolList: List<School>, onSchoolSelected: (School) -> Unit,
    enabled: Boolean, modifier: Modifier = Modifier,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    SelectedSomething(
        Icons.Default.Home,
        school?.name ?: "No school selected",
        { showDialog = true },
        enabled,
        modifier,
    )

    SelectDialog(
        showDialog,
        schoolList, onSchoolSelected,
        "Select school", { it.name }
    ) { showDialog = false }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectedSomething(
    icon: ImageVector, text: String, onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(icon, null)

            Text(text, Modifier.weight(1f))

            Icon(Icons.Default.UnfoldMore, null)
        }
    }
}

@Composable
private fun LoadingBox(
    list: List<Any?>, loading: Boolean, onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        if (list.isEmpty()) {
            if (loading)
                Box(Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(Modifier.size(24.dp))
                }
            else
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> SelectDialog(
    shown: Boolean,
    data: List<T>, onItemSelected: (T) -> Unit,
    title: String, toLabel: (T) -> String,
    onDismissRequest: () -> Unit,
) {
    if (!shown) return

    var search by rememberSaveable { mutableStateOf("") }
    val items = remember(search, data) {
        val searchStripped = search.stripAccents()
        data.filter { toLabel(it).stripAccents().contains(searchStripped) }
    }
    UsableDialog(onDismissRequest) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Text(text = title, style = MaterialTheme.typography.titleLarge)

            LazyColumn(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    Card(
                        onClick = { onItemSelected(item); onDismissRequest() },
                        Modifier.fillMaxWidth(),
                    ) {
                        Box(
                            Modifier
                                .padding(8.dp)
                                .defaultMinSize(minHeight = 48.dp),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(text = toLabel(item))
                        }
                    }
                }
            }
            TextField(
                value = search, onValueChange = { search = it },
                label = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    imeAction = ImeAction.Search,
                ),
            )
        }
    }
}

private fun String.stripAccents(): String {
    var string = Normalizer.normalize(this, Normalizer.Form.NFD)
    string = Regex("\\p{InCombiningDiacriticalMarks}+").replace(string, "")
    return string.lowercase()
}

@Composable
private fun UrlField(
    url: String, onUrlChanged: (String) -> Unit,
    enabled: Boolean, modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        value = url,
        onValueChange = onUrlChanged,
        singleLine = true,
        label = { Text("School server url") },
        leadingIcon = { Icon(Icons.Default.Link, null) },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Next,
        ),
        enabled = enabled,
    )
}

private val Town.nameWithNumber get() = "$name ($schoolCount)"
