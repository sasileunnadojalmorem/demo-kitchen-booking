

package com.oshi.ohsi_back.core.common.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesListItem {
    private int oshiid;
    private String ohsiname;
    private String oshiImage;
    private int categoryid;
    private String categoryname;
    private String categorytype;
    private int goodsid;
    private String goodsname;
    private String goodsImage;
    private String authorid;
    private String authorname;
    private String goodstype;
    
    // Sales 관련 필드
    private int salesid;
    private int salesprice;
    private int salesstatus;
    private String salessellername;
    private String salessellerid;
    private String salesdescription;
}
