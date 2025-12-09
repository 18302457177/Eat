package com.xxs.eat.model.beans

class GoodsInfo {
    var id: Int = 0 //商品id
    var name: String? = null //商品名称
    var icon: String? = null //商品图片
    var form: String? = null //组成
    var monthSaleNum: Int = 0 //月销售量
    var isBargainPrice: Boolean = false //特价
    var isNew: Boolean = false //是否是新产品
    var newPrice: String? = null //新价
    var oldPrice: Int = 0 //原价
    var sellerId: Int = 0
    var typeId: Int = 0
    var typeName: String? = null

    constructor()
    constructor(
        typeName: String?,
        typeId: Int,
        sellerId: Int,
        oldPrice: Int,
        newPrice: String?,
        isNew: Boolean,
        isBargainPrice: Boolean,
        monthSaleNum: Int,
        form: String?,
        icon: String?,
        name: String?,
        id: Int
    ) {
        this.typeName = typeName
        this.typeId = typeId
        this.sellerId = sellerId
        this.oldPrice = oldPrice
        this.newPrice = newPrice
        this.isNew = isNew
        this.isBargainPrice = isBargainPrice
        this.monthSaleNum = monthSaleNum
        this.form = form
        this.icon = icon
        this.name = name
        this.id = id
    }

}
