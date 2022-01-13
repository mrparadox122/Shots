package com.paradox.projectsp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;



import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search);


//        ImageSlider imageSlider = findViewById(R.id.slider);
//        List<SlideModel> slideModels = new ArrayList<>();
//        slideModels.add(new SlideModel("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.dreamstime.com%2Fphotos-images%2Flove.html&psig=AOvVaw0UasAkFs645przHO6c20Ea&ust=1642162474438000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCJiHzonarvUCFQAAAAAdAAAAABAD","1Image"));
//        slideModels.add(new SlideModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.unsplash.com%2Fphoto-1453728013993-6d66e9c9123a%3Fixlib%3Drb-1.2.1%26ixid%3DMnwxMjA3fDB8MHxzZWFyY2h8Mnx8Zm9jdXN8ZW58MHx8MHx8%26w%3D1000%26q%3D80&imgrefurl=https%3A%2F%2Funsplash.com%2Fs%2Fphotos%2Ffocus&tbnid=tTplitM2kjOQtM&vet=12ahUKEwjm3_GI2q71AhWRluYKHUg_AtMQMygIegUIARDiAQ..i&docid=0VsE6im_lxNqHM&w=1000&h=667&itg=1&q=images&ved=2ahUKEwjm3_GI2q71AhWRluYKHUg_AtMQMygIegUIARDiAQ","2Image"));
//        slideModels.add(new SlideModel("https://www.google.com/imgres?imgurl=https%3A%2F%2Fst2.depositphotos.com%2F1141099%2F6198%2Fi%2F600%2Fdepositphotos_61983329-stock-photo-historic-charminar.jpg&imgrefurl=https%3A%2F%2Fdepositphotos.com%2Fstock-photos%2Fhyderabad.html&tbnid=0cVFyq2ej2O38M&vet=12ahUKEwjm3_GI2q71AhWRluYKHUg_AtMQMygKegUIARDnAQ..i&docid=QUH_QBpZyOFtuM&w=600&h=391&itg=1&q=images&ved=2ahUKEwjm3_GI2q71AhWRluYKHUg_AtMQMygKegUIARDnAQ","3Image"));
//        imageSlider.setImageList(slideModels,true);
    }

}