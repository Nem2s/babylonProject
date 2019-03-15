package com.marco.babylonproject;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.marco.babylonproject.model.primitives.Address;
import com.marco.babylonproject.model.primitives.Comment;
import com.marco.babylonproject.model.primitives.Company;
import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.model.primitives.User;
import com.marco.babylonproject.usecase.GetCommentsPerPostUseCase;
import com.marco.babylonproject.usecase.GetPostDetailUseCase;
import com.marco.babylonproject.usecase.GetPostsUseCase;
import com.marco.babylonproject.usecase.GetUserDetailUseCase;
import com.marco.babylonproject.viewmodel.PostDetailsActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostDetailsActivityViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    GetCommentsPerPostUseCase getCommentsPerPostUseCase;

    @Mock
    GetUserDetailUseCase getUserDetailUseCase;

    @Mock
    GetPostDetailUseCase getPostDetailUseCase;


    @Mock
    Observer<Post> postObserver;
    Observer<User> userObserver;
    Observer<List<Comment>> commentObserver;

    private PostDetailsActivityViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new PostDetailsActivityViewModel();
        viewModel.setGetCommentsPerPostUseCase(getCommentsPerPostUseCase);
        viewModel.setGetPostDetailUseCase(getPostDetailUseCase);
        viewModel.setGetUserDetailUseCase(getUserDetailUseCase);
    }

    @Test
    public void getPostDetails_Test() {
        final MutableLiveData<Post> postMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        final MutableLiveData<List<Comment>> commentsLiveData = new MutableLiveData<>();
        Post post = new Post();
        post.setUserId(1L);
        post.setId(1L);
        post.setTitle("Post1");
        post.setBody("Body1");

        User user = new User();
        user.setAddress(new Address());
        user.setCompany(new Company());
        user.setEmail("test@test.com");
        user.setId(12L);
        user.setName("User1");
        user.setPhone("3");
        user.setUsername("username1");
        user.setWebsite("www");

        Comment comment1 = new Comment();
        comment1.setBody("Comm");
        comment1.setEmail("@");
        comment1.setId(123L);
        comment1.setPostId(1L);
        comment1.setName("N");

        Comment comment2 = new Comment();
        comment2.setBody("Cobb");
        comment2.setEmail("@@");
        comment2.setId(1234L);
        comment2.setPostId(1L);
        comment2.setName("NA");

        final List<Comment> comments = new ArrayList<>(Arrays.asList(
                comment1, comment2
        ));
        userMutableLiveData.setValue(user);
        postMutableLiveData.setValue(post);
        commentsLiveData.setValue(comments);

        when(getPostDetailUseCase.execute(post.getId().toString())).thenReturn(postMutableLiveData);
        when(getCommentsPerPostUseCase.execute(post.getId().toString())).thenReturn(commentsLiveData);
        when(getUserDetailUseCase.execute(user.getId().toString())).thenReturn(userMutableLiveData);

        viewModel.observeComments(post.getId().toString()).observeForever(posts);

    }
}
