package dev.gbenga.inaread.ui.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.gbenga.inaread.data.model.SettingKeys
import dev.gbenga.inaread.tokens.DimenTokens
import dev.gbenga.inaread.ui.customs.HorizontalCenter
import dev.gbenga.inaread.ui.customs.InaCard
import dev.gbenga.inaread.ui.customs.UiStateWithLoadingBox
import dev.gbenga.inaread.ui.dialogs.LoadingDialog
import dev.gbenga.inaread.ui.home.InitialComponent
import dev.gbenga.inaread.ui.home.UnitLaunchEffect
import dev.gbenga.inaread.ui.home.VectorInaTextIcon
import dev.gbenga.inaread.ui.metric.AllTimeTitle
import dev.gbenga.inaread.ui.theme.Indigo50
import dev.gbenga.inaread.ui.theme.White
import dev.gbenga.inaread.utils.Scada
import dev.gbenga.inaread.utils.rememberNavigationDelegate
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel,
                   navHostController: NavController
) {

    //val uiState by settingsViewModel.state.collectAsStateWithLifecycle()
    val snackbarHost = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }

    val navDelegate = rememberNavigationDelegate(navHostController)


    val onItemClick = remember {
        { action: SettingKeys ->
            when(action){
                SettingKeys.SIGN_OUT -> {
                    settingsViewModel.logOut()
                }
                else -> {}
            }
        }
    }

    UnitLaunchEffect {
        launch {
            settingsViewModel.navigator.collect {
                navDelegate.handleEvents(it)
            }
        }

        launch {
            settingsViewModel.snackBarEvent.collect {
                snackbarHost.showSnackbar(it)
            }
        }

        launch {
            settingsViewModel.loadingEvent.collect {
                isLoading = it
            }
        }
    }

    LoadingDialog(isLoading)

    Column(modifier = Modifier
        .padding(DimenTokens.Padding.Small)
        .fillMaxSize()) {

        TitledColumn(
            title ="My Profile and Settings",
            subTitle = "Change from light to dark mode. Edit your profile and many more.",
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = DimenTokens.Padding.Normal)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement
                    .spacedBy(DimenTokens.Padding.Normal)
            ) {

                item {
                    ProfileSummaryContent(settingsViewModel)
                }
                item {
                    SettingsContent(settingsViewModel, onItemClick)
                }
            }
        }
    }

}


@Composable
fun SettingsContent(
    settingsViewModel: SettingsViewModel,
    onItemClick: (SettingKeys) -> Unit
) {
    val settingsMenu by settingsViewModel.menuItems.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    AllTimeTitle("Settings")
    SettingsMenu(
        Modifier,
        settingsMenu,
        onItemClick
    )
}

@Composable
fun LazyItemScope.ProfileSummaryContent(settingsViewModel: SettingsViewModel) {
    val settingsUiState by settingsViewModel.state.collectAsStateWithLifecycle()

    AllTimeTitle("Profile")

    UiStateWithLoadingBox(
        settingsUiState.profile,
        errorRequest = {
            Scada.info("error---->: $it")
            InaCard(
                modifier = Modifier
                    .wrapContentHeight()
                    .height(DimenTokens.Size.topbar)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(it, modifier = Modifier.align(Alignment.Center))
                }
            }
        }) { profile ->
        ProfileSummary(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .animateItem(), profile.initial,
            profile.username,
            profile.email
        )
    }


}


@Composable
fun TitledColumn(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    content: @Composable () -> Unit
) {
    HorizontalCenter(
        modifier = modifier
            .padding(DimenTokens.Padding.Normal)
    ) {
        val (titleFont, titleWeight) = MaterialTheme.typography.headlineLarge.let {
            Pair(it.fontSize, it.fontWeight)
        }
        val titleSubTitle = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = titleFont,
                    fontWeight = titleWeight,
                    color = White,
                )
            ) {
                append("$title\n\n")
            }
            subTitle?.let {
                append(subTitle)
            }
        }
        Text(
            titleSubTitle, style = MaterialTheme.typography
                .bodyMedium.copy(
                    fontWeight = FontWeight.W400,
                    color = Indigo50
                ),
            textAlign = TextAlign.Center
        )
        content()
    }
}

@Composable
fun SettingsMenu(
    modifier: Modifier,
    items: List<VectorInaTextIcon>,
    onItemClick: (SettingKeys) -> Unit
) {
    InaCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items.forEachIndexed { index, item ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            item.key?.let {
                                onItemClick.invoke(it as SettingKeys)
                            }

                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(DimenTokens.Icon.Small),
                            tint = Color(item.color)
                        )
                    }
                    Text(
                        item.label,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(
                                horizontal = DimenTokens.Padding.Normal,
                                vertical = DimenTokens.Padding.Normal
                            ),
                    )
                }
                if (index < items.size - 1) {
                    HorizontalDivider(
                        thickness = .5.dp, color = White.copy(alpha = .5f),
                        //  modifier = Modifier.padding(vertical = DimenTokens.Padding.normal),
                    )

                }
            }

        }
    }
}

@Composable
fun ProfileSummary(modifier: Modifier, initial: String, profileName: String, profileEmail: String) {

    InaCard(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .padding(DimenTokens.Padding.Normal)
        ) {
            val (profileAvatar, name, email) = createRefs()

            InitialComponent(
                initial = initial,
                modifier = Modifier.constrainAs(profileAvatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
            Text(
                profileName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(
                        profileAvatar.end,
                        margin = DimenTokens.Padding.Small
                    )
                    top.linkTo(parent.top)
                })
            Text(
                profileEmail, style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(email) {
                    start.linkTo(
                        profileAvatar.end,
                        margin = DimenTokens.Padding.Small
                    )
                    top.linkTo(name.bottom)
                    bottom.linkTo(parent.bottom)
                })
        }
    }

}
