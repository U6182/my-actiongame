/**
 * @file DoorHatredScreen.java
 * @brief 憎しみの扉画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.effectmanager.EffectInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.Pause;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import info.u6182.api.Utiility;
import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.effectmanager.EffectManager;
import info.u6182.api.input.InputManager;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class DoorHatredScreen
 * @brief 憎しみの扉画面のクラス
 */
public class DoorHatredScreen extends BaseScreen {

    //! パスワードキーの数
    private final int KEY_COUNT = 5;

    //! 左キーの角度
    private final float KEY_LEFT = 180f;

    //! 右キーの角度
    private final float KEY_RIGHT = 0f;

    //! 上キーの角度
    private final float KEY_UP = 90f;

    //! 下キーの角度
    private final float KEY_DOWN = 270f;

    //! キーの基準値
    private final float KEY_OFFSET = 100f;

    //! ポーズ
    private Pause pause;

    //! スプライト
    private Sprite keySprite;

    //! キーの座標
    private Vector2 keyPos;

    //! キーの入力数
    private int keyCount;

    //! キーの方向
    private float[] key = {-1f, -1f, -1f, -1f, -1f};

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public DoorHatredScreen(CustomSpriteBatch spriteBatch){

        super(spriteBatch);

        if(GameElement.getGameElement().isStoryLifted()){

            for(int i = 0;i < KEY_COUNT;i++){

                if(i == 0){

                    this.key[i] = KEY_LEFT;

                }else{

                    this.key[i] = KEY_DOWN;

                }

            }

        }

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        //アセットの読み込み
        assetLoad();

        //ワールドカメラの設定
        setCamera(1024f, 768f);

        this.pause = new Pause(this.spriteBatch);

        Texture key = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KEY);

        //キースプライトの初期化
        this.keySprite = new Sprite(key);

        int keyCount = KEY_COUNT - 1;

        //キーの座標の初期化
        this.keyPos = new Vector2((this.camera.viewportWidth - (key.getWidth() * keyCount + KEY_OFFSET * keyCount)) * 0.5f, this.camera.viewportHeight - key.getHeight());

        //キーの入力数の初期化
        this.keyCount = 0;

    }

    /**
     * @brief キーのリセットを行う
     */
    public void reset(){

        //キーの入力数の初期化
        this.keyCount = 0;

        for(int i = 0;i < KEY_COUNT;i++){

            this.key[i] = -1f;

        }

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //操作の更新
        operation();

        //エフェクトの更新
        EffectManager.getEffectManager().update(deltaTime);

    }

    /**
     * @brief 操作の処理を行う
     */
    private void operation(){

        //バックボタンでメニューセレクトスクリーンへ遷移
        if(Gdx.input.isKeyPressed(Keys.BACK)){

            ScreenElement.getElement().setNextScreen(ScreenElement.SCREEN_MENU);

        }

        if(this.pause.update()){

            return;

        }

        //既に解除されている場合処理を行わない
        if(GameElement.getGameElement().isStoryLifted()){

            return;

        }

        //キーの入力
        keyFling();

        //長押しでパスワード確定
        if(InputManager.getInputManager().isLongPress()){

            boolean bAnotherStage = isAnotherStage();

            //パスワードが合っていた場合
            if(bAnotherStage){

                //解除効果音
                SoundManager.getSoundManager().play(SoundInfo.SOUND_LIFTED);

                //バイブレーション
                Gdx.input.vibrate(200);

                //解除エフェクト
                EffectManager.getEffectManager().start(EffectInfo.EFFECT_LIFTED, Utiility.getScreenCenterX(240f), Utiility.getScreenCenterY(240f));

                GameElement.getGameElement().setStoryLifted(bAnotherStage);

            }else{

                SoundManager.getSoundManager().play(SoundInfo.SOUND_FAIL);

                reset();

            }

        }

    }

    /**
     * @brief キーのフリック入力処理を行う
     */
    private void keyFling(){

        //キーの入力が最大入力数以上なら入力を行わない
        if(this.keyCount >= KEY_COUNT){

            return;

        }

        //各キーの入力
        if(InputManager.getInputManager().isLeftFling()){

            this.key[this.keyCount] = KEY_LEFT;

            this.keyCount++;

        }else if(InputManager.getInputManager().isRightFling()){

            this.key[this.keyCount] = KEY_RIGHT;

            this.keyCount++;

        }else if(InputManager.getInputManager().isUpFling()){

            this.key[this.keyCount] = KEY_UP;

            this.keyCount++;

        }else if(InputManager.getInputManager().isDownFling()){

            this.key[this.keyCount] = KEY_DOWN;

            this.keyCount++;

        }

    }

    /**
     * @brief アナザーステージかの確認を行う
     * @return アナザーステージの場合はtrue
     */
    public boolean isAnotherStage(){

        int count = 0;

        boolean bAnotherStage = false;

        for(int i = 0;i < KEY_COUNT;i++){

            if(i == 0){

                if(this.key[i] == KEY_LEFT){

                    count++;

                }

            }else{

                if(this.key[i] == KEY_DOWN){

                    count++;

                }

            }

        }

        if(count == KEY_COUNT){

            bAnotherStage = true;

        }

        return bAnotherStage;

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        update(deltaTime);

        this.spriteBatch.begin();

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Texture backGround;

        if(GameElement.getGameElement().isStoryLifted()){

            backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_DOORHATREDLIFTEDBACKGROUND);

        }else{

            backGround = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_DOORHATREDBACKGROUND);

        }

        //背景の描画
        this.spriteBatch.draw(backGround, 0, 0);

        //エフェクトの描画
        EffectManager.getEffectManager().render();

        //キーの描画
        for(int i = 0;i < this.keyCount;i++){

            this.keySprite.setRotation(this.key[i]);

            this.keySprite.setPosition(this.keyPos.x + (i * KEY_OFFSET + i * this.keySprite.getWidth()), this.keyPos.y);

            this.keySprite.draw(this.spriteBatch);

        }

        this.spriteBatch.end();

        //ポーズの描画
        this.pause.render();

    }

    /**
     * @brief 解放処理を行う
     */
    @Override
    public void release(){

        //テクスチャの解放
        TextureManager.getTextureManager().release();

        //サウンドの解放
        SoundManager.getSoundManager().release();

    }

    /**
     * @brief スクリーンが隠れたときの処理を行う
     */
    @Override
    public void hide(){

        //インスタンスの解放
        release();

    }

    /**
     * @brief ポーズ処理を行う
     */
    @Override
    public void pause(){

        //全てのサウンドの一時停止
        SoundManager.getSoundManager().allPause();

    }

    /**
     * @brief 再開処理を行う
     */
    @Override
    public void resume(){

        //全てのサウンドの再開
        SoundManager.getSoundManager().allResume();

    }

}
