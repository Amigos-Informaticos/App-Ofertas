package com.example.consumirappi.modelo

data class PokemonClass (
    val abilities: List<Ability>,

    val baseExperience: Int,

    val forms: List<Species>,

    val gameIndices: List<GameIndex>,

    val height: Int,

    val heldItems: List<HeldItem>,

    val id: Int,

    val isDefault: Boolean,

    val locationAreaEncounters: String,

    val moves: List<Move>,
    val name: String,
    val order: Int,


    val pastTypes: List<String>,

    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int

)

data class Ability (
    val ability: Species,

    val isHidden: Boolean,

    val slot: Int
)

data class Species (
    val name: String,
    val url: String
)

data class GameIndex (
    val gameIndex: Int,

    val version: Species
)

data class HeldItem (
    val item: Species,

    val versionDetails: List<VersionDetail>
)

data class VersionDetail (
    val rarity: Int,
    val version: Species
)

data class Move (
    val move: Species,

    val versionGroupDetails: List<VersionGroupDetail>
)

data class VersionGroupDetail (
    val levelLearnedAt: Int,

    val moveLearnMethod: Species,

    val versionGroup: Species
)

data class GenerationV (

    val blackWhite: Sprites
)

data class GenerationIv (

    val diamondPearl: Sprites,


    val heartgoldSoulsilver: Sprites,

    val platinum: Sprites
)

data class Versions (

    val generationI: GenerationI,


    val generationIi: GenerationIi,


    val generationIii: GenerationIii,


    val generationIv: GenerationIv,


    val generationV: GenerationV,


    val generationVi: Map<String, GenerationVi>,


    val generationVii: GenerationVii,


    val generationViii: GenerationViii
)

data class Sprites (

    val backDefault: String,


    val backFemale: String,


    val backShiny: String,


    val backShinyFemale: String,


    val frontDefault: String,


    val frontFemale: String,


    val frontShiny: String,


    val frontShinyFemale: String,

    val other: Other,
    val versions: Versions,
    val animated: Sprites

    /*val other: Other? = null,
    val versions: Versions? = null,
    val animated: Sprites? = null*/
)

data class GenerationI (

    val redBlue: RedBlue,

    val yellow: RedBlue
)

data class RedBlue (

    val backDefault: String,


    val backGray: String,


    val frontDefault: String,


    val frontGray: String
)

data class GenerationIi (
    val crystal: Crystal,
    val gold: Crystal,
    val silver: Crystal
)

data class Crystal (

    val backDefault: String,


    val backShiny: String,


    val frontDefault: String,


    val frontShiny: String
)

data class GenerationIii (
    val emerald: Emerald,


    val fireredLeafgreen: Crystal,


    val rubySapphire: Crystal
)

data class Emerald (

    val frontDefault: String,


    val frontShiny: String
)

data class GenerationVi (

    val frontDefault: String,


    val frontFemale: String,


    val frontShiny: String,


    val frontShinyFemale: String
)

data class GenerationVii (
    val icons: DreamWorld,


    val ultraSunUltraMoon: GenerationVi
)

data class DreamWorld (

    val frontDefault: String,


    val frontFemale: String? = null
)

data class GenerationViii (
    val icons: DreamWorld
)

data class Other (

    val dreamWorld: DreamWorld,


    val officialArtwork: OfficialArtwork
)

data class OfficialArtwork (

    val frontDefault: String
)

data class Stat (

    val baseStat: Int,

    val effort: Int,
    val stat: Species
)

data class Type (
    val slot: Int,
    val type: Species
)