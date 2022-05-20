package com.example.mycard.Project.MVVM.Models

/*
head	Заголовок результата (не используется).
def	Массив словарных статей. В атрибуте ts может быть указана транскрипция поискового слова.
tr	Массив переводов.
syn	Массив синонимов.
mean	Массив значений.
ex	Массив примеров.
text	Текст записи, перевод или синоним (обязательно).
pos	Часть речи (может быть опущена).
 */

data class HeadModel(
    val head : Unit,
    val def : List<DictModel>
)

data class DictModel(
    val text : String,
    val pos : String,
    val tr : List<TRModel>,
)

data class TRModel(
    val text : String,
    val pos : String,
    val syn : List<SYNModel>,
    val mean : List<MEANModel>,
    val ex : List<EXModel>,
)
data class SYNModel(
    val text : String
)
data class MEANModel(
    val text : String
)
data class EXModel(
    val text : String,
    val tr : List<TREXModel>
)
data class TREXModel(
    val text : String
)