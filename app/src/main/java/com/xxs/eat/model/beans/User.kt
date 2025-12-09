package com.xxs.eat.model.beans

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "t_user") class User {
    @DatabaseField(generatedId = true)
    var id: Int = 0
    @DatabaseField(columnName = "name")
    var name: String? = null
    @DatabaseField(columnName = "balance")
    var balance: Float = 0f
    @DatabaseField(columnName = "discount")
    var discount: Int = 0
    @DatabaseField(columnName = "integral")
    var integral: Int = 0
    @DatabaseField(columnName = "phone")
    var phone: String? = null
}
