package app_beijingnews.alex.com.beijingnews;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SplashActivity extends Activity {

    private RelativeLayout rl_splashs_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlash);
        rl_splashs_root = (RelativeLayout) findViewById(R.id.rl_splashs_root);

        //动画
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        //添加俩动画无先后顺序
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.setDuration(1500);

        rl_splashs_root.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());

    }

    class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(SplashActivity.this,"播放完成",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
