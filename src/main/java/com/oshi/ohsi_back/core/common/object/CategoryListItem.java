

package com.oshi.ohsi_back.core.common.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListItem {
    private int categoryId;
    private String categoryName;
    private String categoryType;
    private String description;
    private int oshiId;
    private String oshiName;
}
