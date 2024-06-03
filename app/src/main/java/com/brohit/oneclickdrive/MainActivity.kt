package com.brohit.oneclickdrive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.brohit.oneclickdrive.ui.theme.OneClickDriveTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OneClickDriveTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val scrollState = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollState.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = "SET operations") },
                scrollBehavior = scrollState
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    var text1 by rememberSaveable { mutableStateOf("") }
    var text1Error by rememberSaveable { mutableStateOf("") }
    var text2 by rememberSaveable { mutableStateOf("") }
    var text2Error by rememberSaveable { mutableStateOf("") }

    var text3 by rememberSaveable { mutableStateOf("") }
    var text3Error by rememberSaveable { mutableStateOf("") }

    var result by rememberSaveable { mutableStateOf("") }
    var isFocused1 by rememberSaveable { mutableStateOf(false) }
    var isFocused2 by rememberSaveable { mutableStateOf(false) }
    var isFocused3 by rememberSaveable { mutableStateOf(false) }


    var intersection by rememberSaveable { mutableStateOf("") }
    var union by rememberSaveable { mutableStateOf("") }
    var highestNumber by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(state)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val regex = Regex("\\d+(?:\\s*,\\s*\\d+)*,?|\\d*")
        var keyboardType by remember { mutableStateOf(KeyboardType.Number) }
        val keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        )
        TextField(
            value = text1,
            onValueChange = {
                text1 = it
                text1Error = if (!it.trim().matches(regex)) {
                    "Invalid input"
                } else {
                    ""
                }
            },
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        isFocused1 = true
                        isFocused2 = false
                        isFocused3 = false
                    }
                }
                .fillMaxWidth(),
            label = {
                Text("Enter numbers (e.g., 1,3,5,6,8)")
            },
            supportingText = {
                if (text1Error.isNotBlank()) {
                    Text(
                        text = "Please enter valid input.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = text1Error.isNotBlank(),
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                if (isFocused1) {
                    if (keyboardType == KeyboardType.Number) {
                        IconButton(onClick = { keyboardType = KeyboardType.Text }) {
                            Icon(
                                painter = painterResource(id = R.drawable.letter_a),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    } else {
                        IconButton(onClick = { keyboardType = KeyboardType.Number }) {
                            Icon(
                                painter = painterResource(id = R.drawable.number_1),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        )
        TextField(
            value = text2,
            onValueChange = {
                text2 = it
                text2Error = if (!it.trim().matches(regex)) {
                    "Invalid input"
                } else {
                    ""
                }
            },
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        isFocused2 = true
                        isFocused1 = false
                        isFocused3 = false
                    }
                }
                .fillMaxWidth(),
            label = { Text("Enter numbers (e.g., 1,3,5,6,9)") },
            supportingText = {
                if (text2Error.isNotBlank()) {
                    Text(
                        text = "Please enter valid input.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = text2Error.isNotBlank(),
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                if (isFocused2) {
                    if (keyboardType == KeyboardType.Number) {
                        IconButton(onClick = { keyboardType = KeyboardType.Text }) {
                            Icon(
                                painter = painterResource(id = R.drawable.letter_a),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    } else {
                        IconButton(onClick = { keyboardType = KeyboardType.Number }) {
                            Icon(
                                painter = painterResource(id = R.drawable.number_1),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        )
        TextField(
            value = text3,
            onValueChange = {
                text3 = it
                text3Error = if (!it.trim().matches(regex)) {
                    "Invalid input"
                } else {
                    ""
                }
            },
            modifier = Modifier
                .onFocusChanged {
                    if (it.isFocused) {
                        isFocused1 = false
                        isFocused2 = false
                        isFocused3 = true
                    }
                }
                .fillMaxWidth(),
            label = { Text("Enter numbers (e.g., 1,3,5,6,10)") },
            supportingText = {
                if (text3Error.isNotBlank()) {
                    Text(
                        text = "Please enter valid input.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            keyboardOptions = keyboardOptions,
            isError = text3Error.isNotBlank(),
            trailingIcon = {
                if (isFocused3) {
                    if (keyboardType == KeyboardType.Number) {
                        IconButton(onClick = { keyboardType = KeyboardType.Text }) {
                            Icon(
                                painter = painterResource(id = R.drawable.letter_a),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    } else {
                        IconButton(onClick = { keyboardType = KeyboardType.Number }) {
                            Icon(
                                painter = painterResource(id = R.drawable.number_1),
                                contentDescription = "",
                                Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        )
        Button(
            onClick = {
                kotlin.runCatching {
                    scope.launch {

                        intersection = ""
                        union = ""
                        highestNumber = ""
                        delay(50)
                        if (text1.isBlank()) {
                            text1Error = "Input field is empty"
                        }
                        if (text2.isBlank()) {
                            text2Error = "Input field is empty"
                        }
                        if (text3.isBlank()) {
                            text3Error = "Input field is empty"
                        }
                        if (text1.isBlank() || text2.isBlank() || text3.isBlank()) {
                            return@launch
                        }
                        if (text1Error.isNotBlank() || text2Error.isNotBlank() || text3Error.isNotBlank()) {
                            return@launch
                        }

                        val input1 = text1.trim().split(",").mapNotNull {
                            it.trim().toIntOrNull()
                        }
                        val input2 = text2.trim().split(",").mapNotNull {
                            it.trim().toIntOrNull()
                        }
                        val input3 = text3.trim().split(",").mapNotNull {
                            it.trim().toIntOrNull()
                        }
                        val intersect = input1.intersect(input2.toSet())
                            .intersect(input3.toSet())
                            .sorted()
                        val u = (input1 + input2 + input3).distinct().sorted()
                        val max = (input1 + input2 + input3).maxOrNull()

                        intersection = intersect.joinToString(", ")
                        union = u.joinToString(", ")
                        highestNumber = max?.toString() ?: ""
                    }
                }.onFailure {
                    result = "Error: ${it.message}"
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Calculate")
        }

        if (union.isNotBlank()) {
            Column {
                Text(
                    text = "Result",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier.border(
                        1.2.dp,
                        MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(.4f)
                                .fillMaxHeight()
                                .border(.4.dp, MaterialTheme.colorScheme.outlineVariant)
                                .padding(2.dp)
                        ) {
                            Text(
                                text = "Intersection",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(.6f)
                                .fillMaxHeight()
                                .border(.4.dp, MaterialTheme.colorScheme.outlineVariant)
                                .padding(2.dp),
                        ) {
                            Text(
                                text = intersection.ifBlank { "empty" },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = Int.MAX_VALUE
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(.4f)
                                .fillMaxHeight()
                                .border(.4.dp, MaterialTheme.colorScheme.outlineVariant)
                                .padding(2.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "Union",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(.6f)
                                .fillMaxHeight()
                                .border(.4.dp, MaterialTheme.colorScheme.outlineVariant)
                                .padding(2.dp),
                        ) {
                            Text(
                                text = union,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }

                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(.4f)
                                .fillMaxHeight()
                                .border(.4.dp, MaterialTheme.colorScheme.outlineVariant)
                                .padding(2.dp)
                        ) {
                            Text(
                                text = "Highest Number",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(.6f)
                                .fillMaxHeight()
                                .border(.4.dp, MaterialTheme.colorScheme.outlineVariant)
                                .padding(2.dp),
                        ) {
                            Text(
                                text = highestNumber,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                maxLines = Int.MAX_VALUE

                            )
                        }

                    }
                }
            }
        }
        Text(text = result)
    }
}