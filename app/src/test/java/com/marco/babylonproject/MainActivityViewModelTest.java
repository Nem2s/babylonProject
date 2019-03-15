package com.marco.babylonproject;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.marco.babylonproject.model.primitives.Post;
import com.marco.babylonproject.usecase.GetPostsUseCase;
import com.marco.babylonproject.viewmodel.MainActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    GetPostsUseCase useCase;

    @Mock
    Observer<List<Post>> observer;

    private MainActivityViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new MainActivityViewModel();
        viewModel.setUseCase(useCase);
    }

    @Test
    public void getPosts_Test() {
        final MutableLiveData<List<Post>> result = new MutableLiveData<>();
        Post post1 = new Post();
        post1.setId(1L);
        post1.setUserId(1L);
        post1.setBody("AAA");
        post1.setTitle("A");
        Post post2 = new Post();
        post2.setId(2L);
        post2.setUserId(2L);
        post2.setBody("AAA");
        post2.setTitle("A");
        final ArrayList<Post> data = new ArrayList<>(Arrays.asList(
                post1, post2
        ));
        result.setValue(data);
        when(useCase.execute(null)).thenReturn(result);
        viewModel.observePosts().observeForever(observer);
        assert (viewModel.observePosts().getValue() == data);
        assertNull(viewModel.observeError());
        verify(observer).onChanged(result.getValue());

    }

    @Test
    public void getPost_Test_Error() {
        final MutableLiveData<List<Post>> result = new MutableLiveData<>();
        final MutableLiveData<String> errorResult = new MutableLiveData<>();
        result.setValue(null);
        errorResult.setValue("No observer found");
        when(useCase.execute(null)).thenReturn(result);
        when(useCase.errorListener()).thenReturn(errorResult);
        viewModel.observePosts().observeForever(observer);
        assertNull(viewModel.observePosts().getValue());
        assertNotNull(viewModel.observeError());
        verify(observer).onChanged(result.getValue());
    }
}
