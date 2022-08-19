package com.example.jetreddit.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetreddit.R
import com.example.jetreddit.domain.model.PostModel
import com.example.jetreddit.domain.model.PostModel.Companion.DEFAULT_POST
import com.example.jetreddit.theme.RwPrimaryDark

@Composable
fun TextPost(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit = {}
) {
    Post(post, onJoinButtonClick) {
        TextContent(post.text)
    }
}

@Composable
fun TextContent(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        fontSize = 12.sp,
        maxLines = 3,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Preview
@Composable
fun TextPostPreview() {
    Post(DEFAULT_POST) {
        TextContent(DEFAULT_POST.text)
    }
}

@Composable
fun ImagePost(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit={}
) {
    Post(post,onJoinButtonClick) {
        ImageContent(post.image ?: R.drawable.compose_course)
    }
}

@Composable
fun ImageContent(image: Int) {
    val imageAsset = ImageBitmap.imageResource(id = image)
    Image(
        bitmap = imageAsset,
        contentDescription = stringResource(id = R.string.post_header_description),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(imageAsset.width.toFloat() / imageAsset.height),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun ImagePostPreview() {
    Post(DEFAULT_POST) {
        ImageContent(image = DEFAULT_POST.image ?: R.drawable.compose_course)
    }
}

@Composable
fun Post(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(shape = MaterialTheme.shapes.large) {
        Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
            Header(post, onJoinButtonClick)
            Spacer(modifier = Modifier.height(4.dp))
            content.invoke()
            Spacer(modifier = Modifier.height(8.dp))
            PostActions(post)
        }
    }
}

@Composable
fun PostActions(post: PostModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VotingAction(text = post.likes, onUpVoteAction = {}, onDownVoteAction = {})
        PostAction(
            vectorResourceId = R.drawable.ic_comment,
            text = post.comments,
            onClickAction = {})
        PostAction(
            vectorResourceId = R.drawable.ic_share,
            text = stringResource(id = R.string.share),
            onClickAction = {})
        PostAction(
            vectorResourceId = R.drawable.ic_emoji,
            text = stringResource(id = R.string.award),
            onClickAction = {})
    }
}

@Composable
fun PostAction(@DrawableRes vectorResourceId: Int, text: String, onClickAction: () -> Unit) {
    Box(modifier = Modifier.clickable(onClick = onClickAction)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = vectorResourceId),
                contentDescription = stringResource(
                    id = R.string.post_action
                ),
                tint = Color.Gray,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Preview
@Composable
fun PostActionPreview() {
    PostAction(
        vectorResourceId = R.drawable.ic_emoji,
        text = stringResource(id = R.string.award),
        onClickAction = {})
}

@Composable
fun VotingAction(text: String, onUpVoteAction: () -> Unit, onDownVoteAction: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ArrowButton(onUpVoteAction, R.drawable.ic_arrow_upward)
        Text(
            text = text,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        ArrowButton(onDownVoteAction, R.drawable.ic_arrow_downward)
    }
}

@Composable
fun ArrowButton(onClickAction: () -> Unit, arrowResourceId: Int) {
    IconButton(onClick = onClickAction, modifier = Modifier.size(30.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = arrowResourceId),
            contentDescription = stringResource(id = R.string.upvote),
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}

@Preview
@Composable
fun VotingActionPreview() {
    VotingAction(text = "test", onUpVoteAction = { }, onDownVoteAction = {})
}

@Preview
@Composable
fun PostPreview() {
    Post(post = PostModel.DEFAULT_POST)
}

@Composable
fun Header(
    post: PostModel,
    onJoinButtonClick: (Boolean) -> Unit = {}
) {
    Row(modifier = Modifier.padding(start = 16.dp)) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
            contentDescription = stringResource(
                id = R.string.subreddits
            ),
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.subreddit_header, post.subreddit),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.primaryVariant
            )
            Text(
                text = stringResource(id = R.string.post_header, post.username, post.postedTime),
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        JionButton(onJoinButtonClick)
        MoreActionsMenu()
    }
    Title(text = post.title)
}

@Composable
fun MoreActionsMenu() {
    var expended by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expended = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more_actions),
                tint = Color.DarkGray
            )
        }
        DropdownMenu(expanded = expended, onDismissRequest = { expended = false }) {
            CustomDropdownMenuItems(
                vectorResouceId = R.drawable.ic_bookmark,
                text = stringResource(id = R.string.save)
            )
        }
    }
}

@Composable
private fun CustomDropdownMenuItems(
    @DrawableRes vectorResouceId: Int,
    text: String,
    color: Color = RwPrimaryDark,
    onClickAction: () -> Unit = {}
) {
    DropdownMenuItem(onClick = onClickAction) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = ImageVector.vectorResource(id = vectorResouceId),
                contentDescription = stringResource(id = R.string.save),
                tint = color
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontWeight = FontWeight.Medium, color = color)
        }
    }
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        maxLines = 3,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Preview
@Composable
fun HeaderPreview() {
    Header(post = PostModel.DEFAULT_POST)
}