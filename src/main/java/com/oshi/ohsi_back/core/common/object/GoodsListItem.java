


package com.oshi.ohsi_back.core.common.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsListItem {
    private int goodsId;
    private String goodsName;
    private String goodsImage;
    private String description;
    private int viewCount;
    private int favoriteCount;
    private String goodsType;
    private int oshiId;
    private String oshiName;
    private int categoryId;
    private String categoryName;
    private String categoryType;
    private int authorId;
    private String authorName;

}
