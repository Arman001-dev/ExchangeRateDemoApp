package com.exchangeratedemoapp.domain.models

enum class FilterOptionsEnum(val label: String) {
    CODE_A_Z("Code A-Z"),
    CODE_Z_A("Code Z-A"),
    QUOTE_ASC("Quote Asc."),
    QUOTE_DESC("Quote Desc.");

    companion object {
        private val MAP: Map<String, FiltersOptionEnum> = values().associateBy(FiltersOptionEnum::label)
        fun from(label: String?) = MAP[label]
    }
}