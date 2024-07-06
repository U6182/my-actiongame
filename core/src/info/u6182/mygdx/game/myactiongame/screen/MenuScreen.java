/**
 * @file MenuScreen.java
 * @brief メニュー画面のクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.mygdx.game.myactiongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import info.u6182.api.batch.CustomSpriteBatch;
import info.u6182.api.input.InputManager;
import info.u6182.api.soundmanager.SoundInfo;
import info.u6182.api.texturemanager.TextureInfo;
import info.u6182.mygdx.game.myactiongame.GameElement;
import info.u6182.mygdx.game.myactiongame.ScreenElement;

import java.util.ArrayList;

import info.u6182.api.Utiility;
import info.u6182.api.primitive.CustomRectangle;
import info.u6182.api.screeneffectmanager.ScreenEffectManager;
import info.u6182.api.soundmanager.SoundManager;
import info.u6182.api.texturemanager.TextureManager;

/**
 * @class MenuScreen
 * @brief メニュー画面のクラス
 */
public class MenuScreen extends BaseScreen {

    //! メニュー座標
    private Vector2 menuPos = new Vector2();

    //! メニュー矩形
    private CustomRectangle menuRect = new CustomRectangle();

    //! キー矩形
    private CustomRectangle keyRect = new CustomRectangle();

    //! バックキー矩形
    private CustomRectangle backKeyRect = new CustomRectangle();

    //! メニューテクスチャ
    private String[] menu = {TextureInfo.TEXTURE_STORY, TextureInfo.TEXTURE_ANOTHERSTORY, TextureInfo.TEXTURE_DOORHATRED, TextureInfo.TEXTURE_STATUS, TextureInfo.TEXTURE_OPTION, TextureInfo.TEXTURE_CREDIT, TextureInfo.TEXTURE_EXIT};

    //! メニュー背景テクスチャ
    private String[] menuBackGround = {TextureInfo.TEXTURE_STAGE5BACKGROUND, TextureInfo.TEXTURE_ANOTHERSTAGE3BACKGROUND, TextureInfo.TEXTURE_ANOTHERSTAGE1BACKGROUND, TextureInfo.TEXTURE_STAGE1BACKGROUND, TextureInfo.TEXTURE_ANOTHERSTAGE1BACKGROUND, TextureInfo.TEXTURE_GAMEBREAKBACKGROUND, TextureInfo.TEXTURE_GAMEBREAKBACKGROUND};

    //! メニュー番号
    private int menuNo;

    //! 遷移スクリーン
    private int[] nextScreen = {ScreenElement.SCREEN_STORY, ScreenElement.SCREEN_ANOTHERSTORY, ScreenElement.SCREEN_DOORHATRED, ScreenElement.SCREEN_STATUS, ScreenElement.SCREEN_OPTION, ScreenElement.SCREEN_CREDIT, ScreenElement.SCREEN_TITLE};

    /**
     * @brief コンストラクタ
     * @param spriteBatch スプライトバッチ
     */
    public MenuScreen(CustomSpriteBatch spriteBatch){

        //バッチャの設定
        super(spriteBatch);

    }

    /**
     * @brief スクリーンが表示されたときの処理を行う
     */
    @Override
    public void show(){

        assetLoad();

        setCamera(960f, 540f);

        //メニュー番号の初期化
        this.menuNo = GameElement.getGameElement().getMenuNo();

        Texture key = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KEY);

        float keyPosX = this.camera.viewportWidth - key.getWidth();
        float keyPosY = Utiility.getScreenCenterY(key.getHeight());

        //キー矩形の初期化
        this.keyRect.set(keyPosX, keyPosY, key.getWidth(), key.getHeight());

        //バックキー矩形の初期化
        this.backKeyRect.set(0, keyPosY, key.getWidth(), key.getHeight());

    }

    /**
     * @brief 更新処理を行う
     * @param deltaTime 経過時間
     */
    public void update(float deltaTime){

        //遷移エフェクトの更新
        ScreenEffectManager.getScreenEffectManager().rectOrderLined(this.nextScreen[this.menuNo]);

        Texture menu = TextureManager.getTextureManager().getTexture(this.menu[this.menuNo]);

        Vector2 menuPos = Utiility.getScreenCenter(menu.getWidth(), menu.getHeight());

        //メニュー座標の設定
        this.menuPos.set(menuPos.x, menuPos.y);

        //メニュー矩形の設定
        this.menuRect.set(menuPos.x, menuPos.y, menu.getWidth(), menu.getHeight());

        //操作の更新
        operation();

        //ワールドカメラの更新
        this.camera.update();

    }


    /**
     * @brief 操作の処理を行う
     */
    private void operation(){

        if(ScreenElement.getElement().isEffect()){

            return;

        }

        ArrayList<Integer> list = InputManager.getInputManager().getTouchDownList();

        int maxPointer = list.size();

        for(int i = 0;i < maxPointer;i++){

            int pointer = list.get(i);

            this.touchPos.x = Gdx.input.getX(pointer);
            this.touchPos.y = Gdx.input.getY(pointer);

            this.touchPos = this.viewport.unproject(this.touchPos);

            if(this.keyRect.contains(this.touchPos.x, this.touchPos.y)){

                this.menuNo++;

                if(this.menuNo > this.menu.length - 1){

                    this.menuNo = 0;

                }

                SoundManager.getSoundManager().play(SoundInfo.SOUND_STORYPAGE);

            }else if(this.backKeyRect.contains(this.touchPos.x, this.touchPos.y)){

                this.menuNo--;

                if(this.menuNo < 0){

                    this.menuNo = this.menu.length - 1;

                }

                SoundManager.getSoundManager().play(SoundInfo.SOUND_STORYPAGE);

            }

            if(this.menuRect.contains(this.touchPos.x, this.touchPos.y)){

                boolean bNext = true;

                boolean bAnotherStory = false;

                if(this.nextScreen[this.menuNo] == ScreenElement.SCREEN_ANOTHERSTORY){

                    if(!GameElement.getGameElement().isStoryLifted()){

                        SoundManager.getSoundManager().play(SoundInfo.SOUND_FAIL);

                        bNext = false;

                    }else{

                        bAnotherStory = true;

                    }

                }

                if(this.nextScreen[this.menuNo] == ScreenElement.SCREEN_STATUS){

                    if(GameElement.getGameElement().getSettings().bNotPlay){

                        SoundManager.getSoundManager().play(SoundInfo.SOUND_FAIL);

                        bNext = false;

                    }

                }

                if(bNext){


                    if(this.nextScreen[this.menuNo] == ScreenElement.SCREEN_TITLE){

                        GameElement.getGameElement().setMenuNo(0);

                    }else{

                        GameElement.getGameElement().setMenuNo(this.menuNo);

                    }

                    GameElement.getGameElement().setAnotherStory(bAnotherStory);

                    ScreenElement.getElement().setEffect(true);

                    SoundManager.getSoundManager().play(SoundInfo.SOUND_CLICK);

                }

            }

        }

    }

    /**
     * @brief 描画処理を行う
     * @param deltaTime 経過時間
     */
    @Override
    public void render(float deltaTime){

        //更新
        update(deltaTime);

        boolean bNotNext = false;

        switch (this.nextScreen[this.menuNo]){

            case ScreenElement.SCREEN_ANOTHERSTORY:

                if(!GameElement.getGameElement().isStoryLifted()){

                    bNotNext = true;

                }

                break;

            case ScreenElement.SCREEN_STATUS:

                if(GameElement.getGameElement().getSettings().bNotPlay){

                    bNotNext = true;

                }

                break;

        }

        this.spriteBatch.begin();

        if(bNotNext){

            this.spriteBatch.setColor(0.3f, 0.3f, 0.3f, 1);

        }

        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        Vector2 viewSize = GameElement.getGameElement().getViewPortSize();

        Texture backGround = TextureManager.getTextureManager().getTexture(this.menuBackGround[this.menuNo]);

        float th = backGround.getHeight();

        float tw = backGround.getWidth();

        for(int y = 0;y < viewSize.y;y += th){

            for(int x = 0;x < viewSize.x;x += tw){

                this.spriteBatch.draw(backGround, x, y);

            }

        }

        Texture menu = TextureManager.getTextureManager().getTexture(this.menu[this.menuNo]);

        //メニューの描画
        this.spriteBatch.draw(menu, this.menuPos.x, this.menuPos.y);

        Texture key = TextureManager.getTextureManager().getTexture(TextureInfo.TEXTURE_KEY);

        float keyPosX = viewSize.x - key.getWidth();
        float keyPosY = Utiility.getScreenCenterY(key.getHeight());

        //キーの描画
        this.spriteBatch.draw(key, keyPosX, keyPosY);

        //バックキーの描画
        this.spriteBatch.draw(key, 0, keyPosY, true, false);

        if(bNotNext){

            this.spriteBatch.setColor(1, 1, 1, 1);

        }

        this.spriteBatch.end();

        //遷移エフェクトの描画
        ScreenEffectManager.getScreenEffectManager().rectOrderLinedRender(this.camera.combined);

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
