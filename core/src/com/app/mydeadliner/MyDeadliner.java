package com.app.mydeadliner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class MyDeadliner extends ApplicationAdapter {

	private SpriteBatch batch;
	private ShapeRenderer BatchShape;
	private Sprite TestButton;
	private Vector2 PosTestButton, PosCenter;
	private float time = 0.f;
	private static float FadeFactor = 0.f;
	private static final float h=1920.f, w=1080.f, r=20.f, ParticleSize = 10.f;
	private static final int ParticleCount = 9;
	private OrthographicCamera cam;

	@Override
	public void create () {
		PosCenter = new Vector2(w/2.f, h/2.f);
		batch = new SpriteBatch();
		TestButton = new Sprite(new Texture("BUTTON_MENU.png"));
		TestButton.setSize(400.f,200.f);
		//PosTestButton = new Vector2(w/2,h/2);
		BatchShape = new ShapeRenderer();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,w,h);
		batch.setProjectionMatrix(cam.combined);
		BatchShape.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render () {
		time += 0.01f;
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glClearColor(0.85f, 0.f, 0.9f, 1.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();

		BatchShape.begin(ShapeRenderer.ShapeType.Filled);
		drawParticles();
		BatchShape.end();

		batch.enableBlending();
		batch.begin();
		drawButton();
		batch.end();

		if (Gdx.input.isTouched()) {
			System.out.println(" X " + Gdx.input.getX()
					* (w / Gdx.app.getGraphics().getWidth()));
			System.out.println(" Y " + Gdx.input.getY()
					* (h / Gdx.app.getGraphics().getHeight()));
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		TestButton.getTexture().dispose();
	}

	//private void moveButton(){
	//      PosTestButton.add(.15f,.15f);
	//      TestButton.setPosition((float) (-Math.sin(PosTestButton.x)*r + w), (float) (Math.cos(PosTestButton.y)*r + h));
	//}

	private void drawParticles() {
		for (int i = 1; i <= ParticleCount; i++) {
			BatchShape.setColor(1.f-i/5.f,1.f*i/5.f,i/5.f, -0.3f+time);
			BatchShape.circle((float) (-Math.sin(time*i/2.f)*r*i + PosCenter.x),
					(float) (Math.cos(time*i/2.f)*r*i/2.f + PosCenter.y/2.f +
							Interpolation.exp5.apply(time*(i+1.f))*PosCenter.y + Math.sin(time)*h/20.f),
					ParticleSize);
		}
	}

	private void drawButton(){
		if(time > 1.f) FadeFactor = (float) Math.max(FadeFactor, Math.sin(time-1.f));
		TestButton.setColor(1.f,1.f,1.f, FadeFactor);
		TestButton.setCenter(200.f,100.f);

		TestButton.setPosition((w-TestButton.getWidth())/2.f,(h-TestButton.getHeight()/2.f)/4.f);
		TestButton.draw(batch);
	}
}
