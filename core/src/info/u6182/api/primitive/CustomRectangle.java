/**
 * @file CustomRectangle.java
 * @brief 矩形を表すクラスのファイル
 * @author 木村 憂哉
 * @date 2020/04/06
 */
package info.u6182.api.primitive;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.NumberUtils;

/**
 * @class CustomRectangle
 * @brief 矩形を表すクラス
 */
public class CustomRectangle extends Rectangle {

    //! 矩形の左側
    public float left;

    //! 矩形の右側
    public float right;

    //! 矩形の上側
    public float top;

    //! 矩形の下側
    public float buttom;

    /**
     * @brief コンストラクタ
     * @details 矩形の初期化を行う
     */
    public CustomRectangle(){

        super();

    }

    /**
     * @brief コンストラクタ
     * @param pos 座標
     * @param size サイズ
     * @details 座標とサイズを指定して矩形の初期化を行う
     */
    public CustomRectangle(Vector2 pos, Vector2 size){

        this.x = pos.x;

        this.y = pos.y;

        this.left = pos.x;

        this.right = pos.x + size.x;

        this.top = pos.y + size.y;

        this.buttom = pos.y;

        this.width = size.x;

        this.height = size.y;

    }

    /**
     * @brief コンストラクタ
     * @param x 座標X
     * @param y 座標Y
     * @param width 幅
     * @param height 高さ
     * @details 座標、幅、高さを指定して矩形の初期化を行う
     */
    public CustomRectangle(float x, float y, float width, float height){

        super(x, y, width, height);

        this.left = x;

        this.right = x + width;

        this.top = y + height;

        this.buttom = y;

    }

    /**
     * @brief コンストラクタ
     * @param rect 矩形
     * @details 他の矩形を指定して矩形の初期化を行う
     */
    public CustomRectangle(Rectangle rect){

        super(rect);

        this.left = rect.x;

        this.right = rect.x + rect.width;

        this.top = rect.y + rect.height;

        this.buttom = rect.y;

    }

    /**
     * @brief add
     * @param left 左側
     * @param buttom 下側
     * @param right 右側
     * @param top 上側
     * @return CustomRectangle 更新された矩形
     * @details 矩形の各辺を指定して更新を行う
     */
    public CustomRectangle add(float left, float buttom, float right, float top){

        this.x += left;

        this.y += buttom;

        this.left += left;

        this.right += right;

        this.buttom += buttom;

        this.top += top;

        return this;

    }

    /**
     * @brief set
     * @param pos 座標
     * @param size サイズ
     * @return CustomRectangle 更新された矩形
     * @details 座標とサイズを指定して矩形の更新を行う
     */
    public CustomRectangle set(Vector2 pos, Vector2 size){

        this.x = pos.x;

        this.y = pos.y;

        this.left = pos.x;

        this.right = pos.x + size.x;

        this.top = pos.y + size.y;

        this.buttom = pos.y;

        this.width = size.x;

        this.height = size.y;

        return this;

    }

    /**
     * @brief set
     * @param x 座標X
     * @param y 座標Y
     * @param width 幅
     * @param height 高さ
     * @return CustomRectangle 更新された矩形
     * @details 座標、幅、高さを指定して矩形の更新を行う
     */
    @Override
    public CustomRectangle set(float x, float y, float width, float height){

        super.set(x, y, width, height);

        this.left = x;

        this.right = x + width;

        this.top = y + height;

        this.buttom = y;

        return this;

    }

    /**
     * @brief set
     * @param x 座標X
     * @param y 座標Y
     * @param left 左側
     * @param buttom 下側
     * @param right 右側
     * @param top 上側
     * @param width 幅
     * @param height 高さ
     * @return CustomRectangle 更新された矩形
     * @details 座標と矩形の各辺、幅、高さを指定して更新を行う
     */
    public CustomRectangle set(float x, float y, float left, float buttom, float right, float top, float width, float height){

        this.x = x;

        this.y = y;

        this.left = left;

        this.right = right;

        this.top = top;

        this.buttom = buttom;

        this.width = width;

        this.height = height;

        return this;

    }

    /**
     * @brief set
     * @param rect 矩形
     * @return CustomRectangle 更新された矩形
     * @details 他の矩形を指定して更新を行う
     */
    public CustomRectangle set(CustomRectangle rect){

        this.x = rect.x;

        this.y = rect.y;

        this.left = rect.left;

        this.right = rect.right;

        this.top = rect.top;

        this.buttom = rect.buttom;

        this.width = rect.width;

        this.height = rect.height;

        return this;

    }

    /**
     * @brief setCustomRect
     * @param left 左側
     * @param buttom 下側
     * @param right 右側
     * @param top 上側
     * @return CustomRectangle 更新された矩形
     * @details 矩形の各辺を指定して更新を行う
     */
    public CustomRectangle setCustomRect(float left, float buttom, float right, float top){

        this.x = left;

        this.y = buttom;

        this.left = left;

        this.right = right;

        this.top = top;

        this.buttom = buttom;

        this.width = right - left;

        this.height = top - buttom;

        return this;

    }

    /**
     * @brief setX
     * @param xLeft 左側のX座標
     * @return CustomRectangle 更新された矩形
     * @details 左側のX座標を指定して更新を行う
     */
    @Override
    public CustomRectangle setX(float xLeft){

        this.x = xLeft;

        this.left = xLeft;

        return this;

    }

    /**
     * @brief setY
     * @param yButtom 下側のY座標
     * @return CustomRectangle 更新された矩形
     * @details 下側のY座標を指定して更新を行う
     */
    @Override
    public CustomRectangle setY(float yButtom){

        this.y = yButtom;

        this.buttom = yButtom;

        return this;

    }

    /**
     * @brief setRight
     * @param right 右側
     * @return CustomRectangle 更新された矩形
     * @details 右側を指定して更新を行う
     */
    public CustomRectangle setRight(float right){

        this.right = right;

        this.width = right - this.left;

        return this;

    }

    /**
     * @brief setTop
     * @param top 上側
     * @return CustomRectangle 更新された矩形
     * @details 上側を指定して更新を行う
     */
    public CustomRectangle setTop(float top){

        this.top = top;

        this.height = top - this.buttom;

        return this;

    }

    /**
     * @brief setWidth
     * @param width 幅
     * @return CustomRectangle 更新された矩形
     * @details 幅を指定して更新を行う
     */
    @Override
    public CustomRectangle setWidth(float width){

        this.right = this.left + width;

        this.width = width;

        return this;

    }

    /**
     * @brief setHeight
     * @param height 高さ
     * @return CustomRectangle 更新された矩形
     * @details 高さを指定して更新を行う
     */
    @Override
    public CustomRectangle setHeight(float height){

        this.top = this.buttom + height;

        this.height = height;

        return this;

    }

    /**
     * @brief setPosition
     * @param position 座標
     * @return CustomRectangle 更新された矩形
     * @details 座標を指定して更新を行う
     */
    @Override
    public CustomRectangle setPosition(Vector2 position){

        this.x = position.x;

        this.y = position.y;

        this.left = position.x;

        this.buttom = position.y;

        return this;

    }

    /**
     * @brief setPosition
     * @param x 座標X
     * @param y 座標Y
     * @return CustomRectangle 更新された矩形
     * @details 座標を指定して更新を行う
     */
    @Override
    public CustomRectangle setPosition(float x, float y){

        this.x = x;

        this.y = y;

        this.left = x;

        this.buttom = y;

        return this;

    }

    /**
     * @brief setSize
     * @param width 幅
     * @param height 高さ
     * @return CustomRectangle 更新された矩形
     * @details 幅と高さを指定して更新を行う
     */
    @Override
    public CustomRectangle setSize(float width, float height){

        this.right = this.left + width;

        this.top = this.buttom + height;

        this.width = width;

        this.height = height;

        return this;

    }

    /**
     * @brief setSize
     * @param sizeXY サイズ
     * @return CustomRectangle 更新された矩形
     * @details サイズを指定して更新を行う
     */
    @Override
    public CustomRectangle setSize(float sizeXY){

        this.right = this.left + sizeXY;

        this.top = this.buttom + sizeXY;

        this.width = sizeXY;

        this.height = sizeXY;

        return this;

    }

    /**
     * @brief setCenter
     * @param position 中心座標
     * @return CustomRectangle 更新された矩形
     * @details 中心座標を指定して更新を行う
     */
    @Override
    public CustomRectangle setCenter(Vector2 position){

        setPosition(position.x - width / 2, position.y - height / 2);

        return this;

    }

    /**
     * @brief setCenter
     * @param x 中心のX座標
     * @param y 中心のY座標
     * @return CustomRectangle 更新された矩形
     * @details 中心の座標を指定して更新を行う
     */
    @Override
    public CustomRectangle setCenter(float x, float y){

        setPosition(x - width / 2, y - height / 2);

        return this;

    }

    /**
     * @brief merge
     * @param vecs ベクトルの配列
     * @return CustomRectangle 更新された矩形
     * @details ベクトルの配列を指定して更新を行う
     */
    @Override
    public CustomRectangle merge(Vector2[] vecs){

        float minX = this.x;
        float maxX = this.x + this.width;

        float minY = this.y;
        float maxY = this.y + height;

        for(int i = 0;i < vecs.length;++i){

            Vector2 v = vecs[i];

            minX = Math.min(minX, v.x);
            maxX = Math.max(maxX, v.x);

            minY = Math.min(minY, v.y);
            maxY = Math.max(maxY, v.y);

        }

        this.x = minX;

        this.left = minX;

        this.width = maxX - minX;

        this.right = this.left + this.width;

        this.y = minY;

        this.buttom = minY;

        this.height = maxY - minY;

        this.top = this.buttom + this.height;

        return this;

    }

    /**
     * @brief merge
     * @param x 座標X
     * @param y 座標Y
     * @return CustomRectangle 更新された矩形
     * @details 座標を指定して更新を行う
     */
    @Override
    public CustomRectangle merge(float x, float y){

        float minX = Math.min(this.x, x);

        float maxX = Math.max(this.x + this.width, x);

        this.x = minX;

        this.left = minX;

        this.width = maxX - minX;

        this.right = this.left + this.width;

        float minY = Math.min(this.y, y);
        float maxY = Math.max(this.y + this.height, y);

        this.y = minY;

        this.buttom = minY;

        this.height = maxY - minY;

        this.top = this.buttom + this.height;

        return this;

    }

    /**
     * @brief merge
     * @param rect 矩形
     * @return CustomRectangle 更新された矩形
     * @details 他の矩形を指定して更新を行う
     */
    @Override
    public CustomRectangle merge(Rectangle rect){

        float minX = Math.min(this.x, rect.x);
        float maxX = Math.max(this.x + this.width, rect.x + rect.width);

        this.x = minX;

        this.left = minX;

        this.width = maxX - minX;

        this.right = this.left + this.width;

        float minY = Math.min(this.y, rect.y);
        float maxY = Math.max(this.y + this.height, rect.y + rect.height);

        this.y = minY;

        this.buttom = minY;

        this.height = maxY - minY;

        this.top = this.buttom + this.height;

        return this;

    }

    /**
     * @brief merge
     * @param vec ベクトル
     * @return CustomRectangle 更新された矩形
     * @details ベクトルを指定して更新を行う
     */
    @Override
    public CustomRectangle merge(Vector2 vec){

        return merge(vec.x, vec.y);

    }

    /**
     * @brief hashCode
     * @return int ハッシュコード
     * @details ハッシュコードの取得を行う
     */
    @Override
    public int hashCode(){

        final int prime = 31;

        int result = 1;

        result = prime * result + NumberUtils.floatToRawIntBits(this.x);
        result = prime * result + NumberUtils.floatToRawIntBits(this.y);
        result = prime * result + NumberUtils.floatToRawIntBits(this.left);
        result = prime * result + NumberUtils.floatToRawIntBits(this.right);
        result = prime * result + NumberUtils.floatToRawIntBits(this.top);
        result = prime * result + NumberUtils.floatToRawIntBits(this.buttom);
        result = prime * result + NumberUtils.floatToRawIntBits(this.width);
        result = prime * result + NumberUtils.floatToRawIntBits(this.height);

        return result;

    }

    /**
     * @brief equals
     * @param obj オブジェクト
     * @return boolean 等しいかどうか
     * @details 他のオブジェクトと等しいかどうかの判定を行う
     */
    @Override
    public boolean equals(Object obj){

        if(this == obj){

            return true;

        }

        if(obj == null){

            return false;

        }

        if(getClass() != obj.getClass()){

            return false;

        }

        CustomRectangle other = (CustomRectangle)obj;

        if(NumberUtils.floatToRawIntBits(this.x) != NumberUtils.floatToRawIntBits(other.x)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.y) != NumberUtils.floatToRawIntBits(other.y)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.left) != NumberUtils.floatToRawIntBits(other.left)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.right) != NumberUtils.floatToRawIntBits(other.right)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.top) != NumberUtils.floatToRawIntBits(other.top)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.buttom) != NumberUtils.floatToRawIntBits(other.buttom)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.width) != NumberUtils.floatToRawIntBits(other.width)){

            return false;

        }

        if(NumberUtils.floatToRawIntBits(this.height) != NumberUtils.floatToRawIntBits(other.height)){

            return false;

        }

        return true;

    }

}
