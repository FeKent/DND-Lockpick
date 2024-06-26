@file:OptIn(ExperimentalMaterial3Api::class)

package com.fekent.dndlockpick.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fekent.dndlockpick.R
import com.fekent.dndlockpick.data.Mode
import com.fekent.dndlockpick.data.modes
import com.fekent.dndlockpick.ui.theme.DNDLockpickTheme

@Composable
fun ModeScreen(
    modes: List<Mode>,
    modeChoice: (Mode) -> Unit,
    customScreen: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ModeBar()
        Image(
            painter = painterResource(id = R.drawable.js_lockpick_shortened),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp)
        )
        /*TODO
           - Fix wrapping of text if font size/scale is higher
        */
        Spacer(modifier = Modifier.size(32.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(2.dp, color = Color(65, 178, 139, 255))
                    .clickable { customScreen() }
            ) {
                Text(
                    text = "Custom Game", fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp,
                    color = Color(65, 178, 139, 255),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 20.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(32.dp))
            modes.forEach {
                ModeItem(
                    difficulty = it.difficulty,
                    modeTitle = it.modeTitle,
                    modeTumblers = it.modeTumblers,
                    modeTimeLimit = it.modeTimeLimit,
                    modeChoice = { choice ->
                        modeChoice(
                            Mode(
                                choice.difficulty,
                                choice.modeTitle,
                                choice.modeTumblers,
                                choice.modeTimeLimit
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun ModeItem(
    difficulty: String,
    modeTitle: String,
    modeTumblers: Int,
    modeTimeLimit: Int,
    modeChoice: (Mode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(2.dp, color = Color(65, 178, 139, 255))
            .clickable { modeChoice(Mode(difficulty, modeTitle, modeTumblers, modeTimeLimit)) }) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = difficulty,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp,
                    color = Color(65, 178, 139, 255),
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = modeTitle,
                    //fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color(65, 178, 139, 255),
                    modifier = Modifier.align(Alignment.Bottom)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Tumblers: $modeTumblers",
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
                Text(
                    text = "Time Limit: ${modeTimeLimit}s",
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun ModeBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "Mode Selection", fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
        modifier = Modifier
            .padding(4.dp)
            .shadow(
                elevation = 5.dp,
                spotColor = Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
    )
}


@Preview(showSystemUi = true, fontScale = 2f)
@Composable
private fun ModePreview() {
    DNDLockpickTheme {
        ModeScreen(modes, {}, {})
    }
}