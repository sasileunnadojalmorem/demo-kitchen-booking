

package com.oshi.ohsi_back.core.common.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OshiListItem {
    
    private int oshiId;
    private String oshiName;
    private String oshiImage;
    private String description;
}
