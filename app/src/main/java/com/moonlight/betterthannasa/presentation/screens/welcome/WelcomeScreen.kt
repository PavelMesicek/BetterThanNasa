package com.moonlight.betterthannasa.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.moonlight.betterthannasa.domain.model.OnBoardingPage
import com.moonlight.betterthannasa.navigation.Screen
import com.moonlight.betterthannasa.ui.theme.*
import com.moonlight.betterthannasa.util.Constants.LAST_ON_BOARDING_PAGE
import com.moonlight.betterthannasa.util.Constants.ON_BOARDING_PAGE_COUNT

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState()

    if (!viewModel.state.value.isOnBoardingCompleted) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            HorizontalPager(
                modifier = Modifier.weight(10f),
                state = pagerState,
                count = ON_BOARDING_PAGE_COUNT,
                verticalAlignment = Alignment.Top
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }
            HorizontalPagerIndicator(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                pagerState = pagerState,
                activeColor = Purple500,
                inactiveColor = LightGray,
                indicatorWidth = PAGING_INDICATOR_WIDTH,
                spacing = PAGING_INDICATOR_SPACING
            )
            FinishButton(
                modifier = Modifier.weight(1f),
                pagerState = pagerState
            ) {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
                viewModel.onEvent(WelcomeEvent.CompleteOnBoarding)
            }
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "On Boarding Image"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.title,
            color = DarkGray,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING)
                .padding(top = SMALL_PADDING),
            text = onBoardingPage.description,
            color = DarkGray.copy(alpha = 0.5f),
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == LAST_ON_BOARDING_PAGE
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Purple500,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}