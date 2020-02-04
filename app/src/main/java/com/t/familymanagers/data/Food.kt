package com.t.familymanagers.data

import com.t.familymanagers.ui.main.TAB_TITLES

class Food {
    companion object {
        var FOOD_KIND_XINXIAN = TAB_TITLES[0]
        var FOOD_KIND_LINGSHI = TAB_TITLES[1]
        var FOOD_KIND_SUDONG = TAB_TITLES[2]
        var FOOD_KIND_GANHUO = TAB_TITLES[3]
        var FOOD_KIND_MIMIANYOU = TAB_TITLES[4]
        var FOOD_KIND_TIAOLIAO = TAB_TITLES[5]

        var IF_OPEN_TRUE = "true"
        var IF_OPEN_FALSE = "false"

        var IF_NEED_ADD_TRUE = "true"
        var IF_NEED_ADD_FALSE = "false"
    }

    constructor(
        name: String,
        number: Int,
        productionDate: String,
        shelfLive: String,
        ifOpen: String,
        openDate: String,
        ifNeedAdd: String,
        kind: String
    ) {
        this.name = name
        this.number = number
        this.kind = kind
        this.shelfLive = shelfLive
        this.productionDate = productionDate
        this.openDate = openDate
        this.ifOpen = ifOpen
        this.ifNeedAdd = ifNeedAdd
    }

    var name = ""
    var number = 0
    var productionDate = ""
    var shelfLive = ""
    var ifOpen = ""
    var openDate = ""
    var ifNeedAdd = ""
    var kind = ""
    override fun toString(): String {
        return "name=$name,number=$number,kind=$kind,shelfLive=$shelfLive,openDate=$openDate,ifOpen=$ifOpen,ifNeedAdd=$ifNeedAdd"
    }
}