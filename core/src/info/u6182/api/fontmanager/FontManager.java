/**
 * @file FontManager.java
 * @brief フォント管理を行うクラスを定義するファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.fontmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import info.u6182.api.batch.CustomSpriteBatch;

/**
 * @class FontManager
 * @brief フォント管理を行うクラス
 */
public class FontManager {

    //! 自身のインスタンス
    private static final FontManager FONTMANAGER = new FontManager();

    //! スプライトバッチャ
    private CustomSpriteBatch spriteBatch;

    //! ビットマップフォント
    private BitmapFont font;

    //! フォント生成器
    private FreeTypeFontGenerator fontGenerator;

    //! フォントパラメータ
    private FreeTypeFontParameter fontParameter;

    /**
     * @brief コンストラクタ
     * @details フォントの読み込みを行う
     */
    private FontManager(){

        load();

    }

    /**
     * @brief フォントの読み込みを行う
     */
    public void load(){

        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Font/PixelMplus12-Regular.ttf"));

        this.fontParameter = new FreeTypeFontParameter();

        this.fontParameter.size = 12;

        fontParameter.borderWidth = 1;

        this.font = this.fontGenerator.generateFont(this.fontParameter);

    }

    /**
     * @brief フォントの描画を行う
     * @param str 描画する文字列
     * @param x 描画位置のX座標
     * @param y 描画位置のY座標
     */
    public void render(String str, float x, float y){

        if(!isHasChar(str)){

            //文字列を使用できるように設定
            this.fontParameter.characters += str;

            this.font = this.fontGenerator.generateFont(this.fontParameter);

        }

        this.font.draw(this.spriteBatch, str, x, y);

    }

    /**
     * @brief フォントの描画を行う
     * @param str 描画する文字列
     * @param color 文字色
     * @param x 描画位置のX座標
     * @param y 描画位置のY座標
     */
    public void render(String str, Color color,float x, float y){

        boolean bGenerate = false;

        if(!isHasChar(str)){

            this.fontParameter.characters += str;

            bGenerate = true;

        }

        this.font.setColor(color);

        if(bGenerate){

            this.font = this.fontGenerator.generateFont(this.fontParameter);

        }

        this.font.draw(this.spriteBatch, str, x, y);

    }

    /**
     * @brief フォントの描画を行う
     * @param str 描画する文字列
     * @param size フォントサイズ
     * @param x 描画位置のX座標
     * @param y 描画位置のY座標
     */
    public void render(String str,int size, float x, float y){

        boolean bGenerate = false;

        if(!isHasChar(str)){

            this.fontParameter.characters += str;

            bGenerate = true;

        }

        if(this.fontParameter.size != size){

            this.fontParameter.size = size;

            bGenerate = true;

        }

        if(bGenerate){

            this.font = this.fontGenerator.generateFont(this.fontParameter);

        }

        this.font.draw(this.spriteBatch, str, x, y);

    }

    /**
     * @brief フォントの描画を行う
     * @param str 描画する文字列
     * @param color 文字色
     * @param size フォントサイズ
     * @param x 描画位置のX座標
     * @param y 描画位置のY座標
     */
    public void render(String str, Color color, int size, float x, float y){

        boolean bGenerate = false;

        if(!isHasChar(str)){

            this.fontParameter.characters += str;

            bGenerate = true;

        }

        this.font.setColor(color);

        if(this.fontParameter.size != size){

            this.fontParameter.size = size;

            bGenerate = true;

        }

        if(bGenerate){

            this.font = this.fontGenerator.generateFont(this.fontParameter);

        }

        this.font.draw(this.spriteBatch, str, x, y);

    }

    /**
     * @brief フォントの解放を行う
     */
    public void release(){

        this.font.dispose();

        this.fontGenerator.dispose();

    }

    /**
     * @brief 指定された文字列がフォントに含まれているかの確認を行う
     * @param str 確認する文字列
     * @return boolean true 含まれている false 含まれていない
     */
    private boolean isHasChar(String str){

        int fpcLength = this.fontParameter.characters.length();

        int fgcLength = FreeTypeFontGenerator.DEFAULT_CHARS.length();

        for(int i = 0;i < str.length();i++){

            char sc = str.charAt(i);

            if(sc <= fgcLength){

                continue;

            }

            if(fgcLength == fpcLength){

                return false;

            }

            for(int j = fgcLength;j < fpcLength;j++){

                char pc = this.fontParameter.characters.charAt(j);

                if(pc == sc){

                    break;

                }

                if(j == fpcLength - 1){

                    return false;

                }

            }

        }

        return true;

    }

    /**
     * @brief スプライトバッチャの設定を行う
     * @param spriteBatch スプライトバッチャ
     */
    public void setSpriteBatch(CustomSpriteBatch spriteBatch){

        this.spriteBatch = spriteBatch;

    }

    /**
     * @brief フォントサイズの設定を行う
     * @param size フォントサイズ
     */
    public void setSize(int size){

        if(this.fontParameter.size != size){

            this.fontParameter.size = size;

            this.font = this.fontGenerator.generateFont(this.fontParameter);

        }

    }

    /**
     * @brief フォントサイズの取得を行う
     * @return int フォントサイズ
     */
    public int getSize(){

        return this.fontParameter.size;

    }

    /**
     * @brief フォントマネージャインスタンスの取得を行う
     * @return FontManager フォントマネージャのインスタンス
     */
    public static FontManager getFontmanager(){

        return FONTMANAGER;

    }

}
