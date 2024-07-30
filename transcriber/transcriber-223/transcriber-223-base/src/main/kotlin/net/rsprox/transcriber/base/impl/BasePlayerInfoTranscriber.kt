package net.rsprox.transcriber.base.impl

import net.rsprox.protocol.common.CoordGrid
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.PlayerInfo
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.PlayerUpdateType
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.extendedinfo.AppearanceExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.extendedinfo.ChatExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.extendedinfo.FaceAngleExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.extendedinfo.MoveSpeedExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.extendedinfo.NameExtrasExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.playerinfo.extendedinfo.TemporaryMoveSpeedExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.ExactMoveExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.ExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.FacePathingEntityExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.HitExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.SayExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.SequenceExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.SpotanimExtendedInfo
import net.rsprox.protocol.game.outgoing.model.info.shared.extendedinfo.TintingExtendedInfo
import net.rsprox.shared.ScriptVarType
import net.rsprox.shared.SessionMonitor
import net.rsprox.shared.property.ChildProperty
import net.rsprox.shared.property.NamedEnum
import net.rsprox.shared.property.Property
import net.rsprox.shared.property.RootProperty
import net.rsprox.shared.property.boolean
import net.rsprox.shared.property.coordGrid
import net.rsprox.shared.property.filteredBoolean
import net.rsprox.shared.property.filteredInt
import net.rsprox.shared.property.filteredScriptVarType
import net.rsprox.shared.property.filteredString
import net.rsprox.shared.property.group
import net.rsprox.shared.property.identifiedNpc
import net.rsprox.shared.property.identifiedPlayer
import net.rsprox.shared.property.int
import net.rsprox.shared.property.namedEnum
import net.rsprox.shared.property.regular.ScriptVarTypeProperty
import net.rsprox.shared.property.scriptVarType
import net.rsprox.shared.property.string
import net.rsprox.shared.property.unidentifiedNpc
import net.rsprox.shared.property.unidentifiedPlayer
import net.rsprox.transcriber.base.firstOfInstanceOfNull
import net.rsprox.transcriber.base.maxUShortToMinusOne
import net.rsprox.transcriber.impl.PlayerInfoTranscriber
import net.rsprox.transcriber.state.Player
import net.rsprox.transcriber.state.StateTracker

@Suppress("DuplicatedCode")
public class BasePlayerInfoTranscriber(
    private val stateTracker: StateTracker,
    private val monitor: SessionMonitor<*>,
) : PlayerInfoTranscriber {
    private val root: RootProperty<*>
        get() = stateTracker.root

    private fun Property.entity(ambiguousIndex: Int): ChildProperty<*> {
        return if (ambiguousIndex > 0xFFFF) {
            player(ambiguousIndex - 0xFFFF)
        } else {
            npc(ambiguousIndex)
        }
    }

    private fun Property.npc(index: Int): ChildProperty<*> {
        val npc = stateTracker.getActiveWorld().getNpcOrNull(index)
        return if (npc != null) {
            identifiedNpc(
                index,
                npc.id,
                npc.name ?: "null",
                npc.coord.level,
                npc.coord.x,
                npc.coord.z,
            )
        } else {
            unidentifiedNpc(index)
        }
    }

    private fun Property.player(
        index: Int,
        name: String = "player",
    ): ChildProperty<*> {
        val player = stateTracker.getPlayerOrNull(index)
        return if (player != null) {
            identifiedPlayer(
                index,
                player.name,
                player.coord.level,
                player.coord.x,
                player.coord.z,
                name,
            )
        } else {
            unidentifiedPlayer(index, name)
        }
    }

    private fun Property.coordGrid(
        name: String,
        coordGrid: CoordGrid,
    ): ScriptVarTypeProperty<*> {
        return coordGrid(coordGrid.level, coordGrid.x, coordGrid.z, name)
    }

    private fun loadPlayerName(
        index: Int,
        extendedInfo: List<ExtendedInfo>,
    ): String {
        val appearance =
            extendedInfo
                .filterIsInstance<AppearanceExtendedInfo>()
                .singleOrNull()
        return appearance?.name
            ?: stateTracker.getLastKnownPlayerName(index)
            ?: "null"
    }

    private fun preloadPlayerInfo(message: PlayerInfo) {
        for ((index, update) in message.updates) {
            when (update) {
                is PlayerUpdateType.LowResolutionToHighResolution -> {
                    val name = loadPlayerName(index, update.extendedInfo)
                    stateTracker.overridePlayer(Player(index, name, update.coord))
                    preprocessExtendedInfo(index, update.extendedInfo)
                }
                is PlayerUpdateType.HighResolutionIdle -> {
                    val name = loadPlayerName(index, update.extendedInfo)
                    val player = stateTracker.getPlayer(index)
                    stateTracker.overridePlayer(Player(index, name, player.coord))
                    preprocessExtendedInfo(index, update.extendedInfo)
                }
                is PlayerUpdateType.HighResolutionMovement -> {
                    val name = loadPlayerName(index, update.extendedInfo)
                    val player = stateTracker.getPlayer(index)
                    stateTracker.overridePlayer(Player(index, name, player.coord))
                    preprocessExtendedInfo(index, update.extendedInfo)
                }
                else -> {
                    // No-op, no info to preload
                }
            }
        }
    }

    private fun preprocessExtendedInfo(
        index: Int,
        extendedInfo: List<ExtendedInfo>,
    ) {
        val moveSpeed = extendedInfo.firstOfInstanceOfNull<MoveSpeedExtendedInfo>()
        if (moveSpeed != null) {
            stateTracker.setCachedMoveSpeed(index, moveSpeed.speed)
        }
        val tempMoveSpeed = extendedInfo.firstOfInstanceOfNull<TemporaryMoveSpeedExtendedInfo>()
        if (tempMoveSpeed != null) {
            stateTracker.setTempMoveSpeed(index, tempMoveSpeed.speed)
        }
        if (index == stateTracker.localPlayerIndex) {
            val appearance = extendedInfo.firstOfInstanceOfNull<AppearanceExtendedInfo>()
            if (appearance != null) {
                monitor.onNameUpdate(appearance.name)
            }
        }
    }

    private fun postPlayerInfo(message: PlayerInfo) {
        for ((index, update) in message.updates) {
            when (update) {
                is PlayerUpdateType.LowResolutionToHighResolution -> {
                    val name = loadPlayerName(index, update.extendedInfo)
                    stateTracker.overridePlayer(Player(index, name, update.coord))
                }
                is PlayerUpdateType.HighResolutionIdle -> {
                    val oldPlayer = stateTracker.getPlayerOrNull(index) ?: return
                    val name = loadPlayerName(index, update.extendedInfo)
                    stateTracker.overridePlayer(Player(index, name, oldPlayer.coord))
                }
                is PlayerUpdateType.HighResolutionMovement -> {
                    val name = loadPlayerName(index, update.extendedInfo)
                    stateTracker.overridePlayer(Player(index, name, update.coord))
                }
                else -> {
                    // No-op, no info to preload
                }
            }
        }
    }

    override fun playerInfo(message: PlayerInfo) {
        stateTracker.clearTempMoveSpeeds()
        // Assign the coord and name of each player that is being added
        preloadPlayerInfo(message)
        // Log any activities that happened for all the players
        logPlayerInfo(message)
        // Update the last known coord and name of each player being processed
        postPlayerInfo(message)
    }

    private fun logPlayerInfo(message: PlayerInfo) {
        val group =
            root.group {
                for ((index, update) in message.updates) {
                    when (update) {
                        is PlayerUpdateType.LowResolutionMovement, PlayerUpdateType.LowResolutionIdle -> {
                            // no-op
                        }
                        is PlayerUpdateType.HighResolutionIdle -> {
                            if (update.extendedInfo.isEmpty()) {
                                return@group
                            }
                            val player = stateTracker.getPlayer(index)
                            group("IDLE") {
                                player(index)
                                appendExtendedInfo(player, update.extendedInfo)
                            }
                        }
                        is PlayerUpdateType.HighResolutionMovement -> {
                            val player = stateTracker.getPlayer(index)
                            val speed = getMoveSpeed(stateTracker.getMoveSpeed(index))
                            val label =
                                when (speed) {
                                    MoveSpeed.CRAWL -> "CRAWL"
                                    MoveSpeed.WALK -> "WALK"
                                    MoveSpeed.RUN -> "RUN"
                                    MoveSpeed.TELEPORT -> "TELEPORT"
                                    MoveSpeed.STATIONARY -> "STATIONARY"
                                }
                            group(label) {
                                player(index)
                                coordGrid("newcoord", update.coord)
                                appendExtendedInfo(player, update.extendedInfo)
                            }
                        }
                        is PlayerUpdateType.HighResolutionToLowResolution -> {
                            group("DEL") {
                                player(index)
                            }
                        }
                        is PlayerUpdateType.LowResolutionToHighResolution -> {
                            val player = stateTracker.getPlayer(index)
                            group("ADD") {
                                player(index)
                                appendExtendedInfo(player, update.extendedInfo)
                            }
                        }
                    }
                }
            }
        val children = group.children
        // If no children were added to the root group, it means no players are being updated
        // In this case, remove the empty line that the group is generating
        if (children.isEmpty()) {
            root.children.clear()
            return
        }
        // Remove the empty line generated by the wrapper group
        root.children.clear()
        root.children.addAll(children)
    }

    private fun Property.appendExtendedInfo(
        player: Player,
        extendedInfo: List<ExtendedInfo>,
    ) {
        for (info in extendedInfo) {
            when (info) {
                is ChatExtendedInfo -> {
                    group("CHAT") {
                        appendChatExtendedInfo(info)
                    }
                }
                is FaceAngleExtendedInfo -> {
                    group("FACE_ANGLE") {
                        appendFaceAngleExtendedInfo(info)
                    }
                }
                is MoveSpeedExtendedInfo -> {
                    group("MOVE_SPEED") {
                        appendMoveSpeedExtendedInfo(info)
                    }
                }
                is TemporaryMoveSpeedExtendedInfo -> {
                    group("TEMP_MOVE_SPEED") {
                        appendTemporaryMoveSpeedExtendedInfo(info)
                    }
                }
                is NameExtrasExtendedInfo -> {
                    group("NAME_EXTRAS") {
                        appendNameExtrasExtendedInfo(info)
                    }
                }
                is SayExtendedInfo -> {
                    group("SAY") {
                        appendSayExtendedInfo(info)
                    }
                }
                is SequenceExtendedInfo -> {
                    group("SEQUENCE") {
                        appendSequenceExtendedInfo(info)
                    }
                }
                is ExactMoveExtendedInfo -> {
                    group("EXACTMOVE") {
                        appendExactMoveExtendedInfo(player, info)
                    }
                }
                is HitExtendedInfo -> {
                    appendHitExtendedInfo(info)
                }
                is TintingExtendedInfo -> {
                    group("TINTING") {
                        appendTintingExtendedInfo(info)
                    }
                }
                is SpotanimExtendedInfo -> {
                    appendSpotanimExtendedInfo(info)
                }
                is FacePathingEntityExtendedInfo -> {
                    group("FACE_PATHINGENTITY") {
                        appendFacePathingEntityExtendedInfo(info)
                    }
                }
                is AppearanceExtendedInfo -> {
                    group("APPEARANCE") {
                        appendAppearanceExtendedInfo(info)
                    }
                }
                else -> error("Unknown extended info: $info")
            }
        }
    }

    private fun Property.appendChatExtendedInfo(info: ChatExtendedInfo) {
        string("text", info.text)
        filteredBoolean("autotyper", info.autotyper)
        filteredInt("colour", info.colour, 0)
        filteredInt("effects", info.effects, 0)
        filteredInt("chatcrown", info.modIcon, 0)
        filteredString("pattern", info.pattern?.contentToString(), null)
    }

    private fun Property.appendFaceAngleExtendedInfo(info: FaceAngleExtendedInfo) {
        int("angle", info.angle)
    }

    private enum class MoveSpeed(
        override val prettyName: String,
    ) : NamedEnum {
        CRAWL("crawl"),
        WALK("walk"),
        RUN("run"),
        TELEPORT("teleport"),
        STATIONARY("stationary"),
    }

    private fun getMoveSpeed(id: Int): MoveSpeed {
        return when (id) {
            0 -> MoveSpeed.CRAWL
            1 -> MoveSpeed.WALK
            2 -> MoveSpeed.RUN
            127 -> MoveSpeed.TELEPORT
            -1, 255 -> MoveSpeed.STATIONARY // TODO: Figure out when 127 vs -1 gets used
            else -> error("Unknown move speed: $id")
        }
    }

    private fun Property.appendMoveSpeedExtendedInfo(info: MoveSpeedExtendedInfo) {
        namedEnum("speed", getMoveSpeed(info.speed))
    }

    private fun Property.appendTemporaryMoveSpeedExtendedInfo(info: TemporaryMoveSpeedExtendedInfo) {
        namedEnum("speed", getMoveSpeed(info.speed))
    }

    private fun Property.appendNameExtrasExtendedInfo(info: NameExtrasExtendedInfo) {
        string("beforename", info.beforeName)
        string("aftername", info.afterName)
        string("afterlevel", info.afterCombatLevel)
    }

    private fun Property.appendSayExtendedInfo(info: SayExtendedInfo) {
        string("text", info.text)
    }

    private fun Property.appendSequenceExtendedInfo(info: SequenceExtendedInfo) {
        scriptVarType("id", ScriptVarType.SEQ, info.id.maxUShortToMinusOne())
        filteredInt("delay", info.delay, 0)
    }

    private fun Property.appendExactMoveExtendedInfo(
        player: Player,
        info: ExactMoveExtendedInfo,
    ) {
        val curX = player.coord.x
        val curZ = player.coord.z
        val level = player.coord.level
        coordGrid("to1", CoordGrid(level, curX - info.deltaX2, curZ - info.deltaZ2))
        int("delay1", info.delay1)
        coordGrid("to2", CoordGrid(level, curX - info.deltaX1, curZ - info.deltaZ1))
        int("delay2", info.delay2)
        int("angle", info.direction)
    }

    private fun Property.appendHitExtendedInfo(info: HitExtendedInfo) {
        for (hit in info.hits) {
            group("HIT") {
                int("type", hit.type)
                int("value", hit.value)
                if (hit.soakType != -1) {
                    int("soaktype", hit.soakType)
                    int("soakvalue", hit.soakValue)
                }
                filteredInt("delay", hit.delay, 0)
            }
        }
        for (headbar in info.headbars) {
            group("HEADBAR") {
                int("type", headbar.type)
                if (headbar.startFill == headbar.endFill) {
                    int("fill", headbar.startFill)
                } else {
                    int("startfill", headbar.startFill)
                    int("endfill", headbar.endFill)
                }
                if (headbar.startTime != 0 || headbar.endTime != 0) {
                    int("starttime", headbar.startTime)
                    int("endtime", headbar.endTime)
                }
            }
        }
    }

    private fun Property.appendTintingExtendedInfo(info: TintingExtendedInfo) {
        int("start", info.start)
        int("end", info.end)
        int("hue", info.hue)
        int("saturation", info.saturation)
        int("lightness", info.lightness)
        int("weight", info.weight)
    }

    private fun Property.appendSpotanimExtendedInfo(info: SpotanimExtendedInfo) {
        for ((slot, spotanim) in info.spotanims) {
            group("SPOTANIM") {
                filteredInt("slot", slot, 0)
                scriptVarType("id", ScriptVarType.SPOTANIM, spotanim.id)
                filteredInt("delay", spotanim.delay, 0)
                filteredInt("height", spotanim.height, 0)
            }
        }
    }

    private fun Property.appendFacePathingEntityExtendedInfo(info: FacePathingEntityExtendedInfo) {
        if (info.index == 0xFFFFFF) {
            string("entity", null)
        } else {
            entity(info.index)
        }
    }

    private enum class WearPos(
        val id: Int,
        override val prettyName: String,
    ) : NamedEnum {
        HAT(0, "hat"),
        BACK(1, "back"),
        FRONT(2, "front"),
        RIGHTHAND(3, "righthand"),
        TORSO(4, "torso"),
        LEFTHAND(5, "lefthand"),
        ARMS(6, "arms"),
        LEGS(7, "legs"),
        HEAD(8, "head"),
        HANDS(9, "hands"),
        FEET(10, "feet"),
        JAW(11, "jaw"),
        RING(12, "ring"),
        QUIVER(13, "quiver"),
    }

    private fun Property.appendAppearanceExtendedInfo(info: AppearanceExtendedInfo) {
        group("DETAILS") {
            string("name", info.name)
            int("combatlevel", info.combatLevel)
            filteredInt("skilllevel", info.skillLevel, 0)
            int("gender", info.gender)
            int("textgender", info.textGender)
        }
        val statusGroup =
            group("STATUS") {
                filteredBoolean("hidden", info.hidden)
                filteredInt("skullicon", info.skullIcon, -1)
                filteredInt("overheadicon", info.overheadIcon, -1)
                filteredScriptVarType("npc", ScriptVarType.NPC, info.transformedNpcId, -1)
            }
        if (statusGroup.children.isEmpty()) {
            children.removeLast()
        }
        if (info.transformedNpcId == -1) {
            group("EQUIPMENT") {
                for ((index, value) in info.identKit.withIndex()) {
                    if (value >= 512) {
                        val pos = WearPos.entries.first { it.id == index }
                        group {
                            namedEnum("wearpos", pos)
                            scriptVarType("id", ScriptVarType.OBJ, value - 512)
                        }
                    }
                }
            }
            val identKit = IntArray(info.identKit.size)
            group("IDENTKIT") {
                for ((index, value) in info.identKit.withIndex()) {
                    if (value in 256..<512) {
                        identKit[index] = value
                        val pos = WearPos.entries.first { it.id == index }
                        group {
                            namedEnum("wearpos", pos)
                            scriptVarType("id", ScriptVarType.IDKIT, value - 256)
                        }
                    }
                }
            }
            if (!identKit.contentEquals(info.interfaceIdentKit)) {
                group("INTERFACE_IDENTKIT") {
                    for ((index, value) in info.interfaceIdentKit.withIndex()) {
                        if (value in 256..<512) {
                            identKit[index] = value
                            val pos = WearPos.entries.first { it.id == index }
                            group {
                                namedEnum("wearpos", pos)
                                scriptVarType("id", ScriptVarType.IDKIT, value - 256)
                            }
                        }
                    }
                }
            }
        }
        group("COLOURS") {
            for ((index, value) in info.colours.withIndex()) {
                int("colour$index", value)
            }
        }
        group("BAS") {
            scriptVarType("ready", ScriptVarType.SEQ, info.readyAnim.maxUShortToMinusOne())
            scriptVarType("turn", ScriptVarType.SEQ, info.turnAnim.maxUShortToMinusOne())
            scriptVarType("walk", ScriptVarType.SEQ, info.walkAnim.maxUShortToMinusOne())
            scriptVarType("walkback", ScriptVarType.SEQ, info.walkAnimBack.maxUShortToMinusOne())
            scriptVarType("walkleft", ScriptVarType.SEQ, info.walkAnimLeft.maxUShortToMinusOne())
            scriptVarType("walkright", ScriptVarType.SEQ, info.walkAnimRight.maxUShortToMinusOne())
            scriptVarType("run", ScriptVarType.SEQ, info.runAnim.maxUShortToMinusOne())
        }
        group("NAME_EXTRAS") {
            string("beforename", info.beforeName)
            string("aftername", info.afterName)
            string("afterlevel", info.afterCombatLevel)
        }
        val objTypeCustomisationGroup =
            group("OBJ_TYPE_CUSTOMISATION") {
                boolean("forcemodelrefresh", info.forceModelRefresh)
                val customisation = info.objTypeCustomisation
                if (customisation != null) {
                    for ((index, cus) in customisation.withIndex()) {
                        if (cus == null) {
                            continue
                        }
                        val pos = WearPos.entries.first { it.id == index }
                        namedEnum("wearpos", pos)
                        val recolIndex1 = cus.recolIndices and 0xF
                        val recolIndex2 = cus.recolIndices ushr 4 and 0xF
                        if (recolIndex1 != 0xF) {
                            int("recol$recolIndex1", cus.recol1)
                        }
                        if (recolIndex2 != 0xF) {
                            int("recol$recolIndex2", cus.recol2)
                        }

                        val retexIndex1 = cus.retexIndices and 0xF
                        val retexIndex2 = cus.retexIndices ushr 4 and 0xF
                        if (retexIndex1 != 0xF) {
                            scriptVarType("retex$retexIndex1", ScriptVarType.TEXTURE, cus.retex1)
                        }
                        if (retexIndex2 != 0xF) {
                            scriptVarType("retex$retexIndex2", ScriptVarType.TEXTURE, cus.retex2)
                        }
                    }
                }
            }
        if (objTypeCustomisationGroup.children.isEmpty()) {
            children.removeLast()
        }
    }
}
