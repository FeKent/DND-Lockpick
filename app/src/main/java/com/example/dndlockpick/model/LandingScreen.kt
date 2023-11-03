@file:OptIn(ExperimentalMaterial3Api::class)

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dndlockpick.viewmodel.LandingScreenViewModel


@Composable
fun LandingScreen(
    landingViewModel: LandingScreenViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state = landingViewModel.tumbler.collectAsState()
    val time = landingViewModel.timeLimit.collectAsState()

    Column(Modifier.fillMaxWidth()) {
        LandingScreenBar()
        Spacer(modifier = Modifier.size(32.dp))
        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Choose amount of tumblers:")
            TextField(
                value = state.value,
                onValueChange = { landingViewModel.tumbler.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(text = "Time Limit")
            TextField(
                value = time.value,
                onValueChange = { landingViewModel.timeLimit.value = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.size(32.dp))
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "GO!")
            }
        }
    }
}

@Composable
fun LandingScreenBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Lockpick Mini-Game",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Menu, "Menu")
        }
    })
}