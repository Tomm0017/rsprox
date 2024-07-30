package net.rsprox.transcriber.base.impl

import net.rsprox.cache.api.Cache
import net.rsprox.cache.api.type.VarBitType
import net.rsprox.protocol.common.CoordGrid
import net.rsprox.protocol.game.outgoing.model.camera.CamLookAt
import net.rsprox.protocol.game.outgoing.model.camera.CamLookAtEasedCoord
import net.rsprox.protocol.game.outgoing.model.camera.CamMode
import net.rsprox.protocol.game.outgoing.model.camera.CamMoveTo
import net.rsprox.protocol.game.outgoing.model.camera.CamMoveToArc
import net.rsprox.protocol.game.outgoing.model.camera.CamMoveToCycles
import net.rsprox.protocol.game.outgoing.model.camera.CamReset
import net.rsprox.protocol.game.outgoing.model.camera.CamRotateBy
import net.rsprox.protocol.game.outgoing.model.camera.CamRotateTo
import net.rsprox.protocol.game.outgoing.model.camera.CamShake
import net.rsprox.protocol.game.outgoing.model.camera.CamSmoothReset
import net.rsprox.protocol.game.outgoing.model.camera.CamTarget
import net.rsprox.protocol.game.outgoing.model.camera.CamTargetOld
import net.rsprox.protocol.game.outgoing.model.camera.OculusSync
import net.rsprox.protocol.game.outgoing.model.clan.ClanChannelDelta
import net.rsprox.protocol.game.outgoing.model.clan.ClanChannelFull
import net.rsprox.protocol.game.outgoing.model.clan.ClanSettingsDelta
import net.rsprox.protocol.game.outgoing.model.clan.ClanSettingsFull
import net.rsprox.protocol.game.outgoing.model.clan.MessageClanChannel
import net.rsprox.protocol.game.outgoing.model.clan.MessageClanChannelSystem
import net.rsprox.protocol.game.outgoing.model.clan.VarClan
import net.rsprox.protocol.game.outgoing.model.clan.VarClanDisable
import net.rsprox.protocol.game.outgoing.model.clan.VarClanEnable
import net.rsprox.protocol.game.outgoing.model.friendchat.MessageFriendChannel
import net.rsprox.protocol.game.outgoing.model.friendchat.UpdateFriendChatChannelFullV1
import net.rsprox.protocol.game.outgoing.model.friendchat.UpdateFriendChatChannelFullV2
import net.rsprox.protocol.game.outgoing.model.friendchat.UpdateFriendChatChannelSingleUser
import net.rsprox.protocol.game.outgoing.model.info.npcinfo.SetNpcUpdateOrigin
import net.rsprox.protocol.game.outgoing.model.info.worldentityinfo.WorldEntityInfo
import net.rsprox.protocol.game.outgoing.model.info.worldentityinfo.WorldEntityUpdateType
import net.rsprox.protocol.game.outgoing.model.interfaces.IfClearInv
import net.rsprox.protocol.game.outgoing.model.interfaces.IfCloseSub
import net.rsprox.protocol.game.outgoing.model.interfaces.IfMoveSub
import net.rsprox.protocol.game.outgoing.model.interfaces.IfOpenSub
import net.rsprox.protocol.game.outgoing.model.interfaces.IfOpenTop
import net.rsprox.protocol.game.outgoing.model.interfaces.IfResync
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetAngle
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetAnim
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetColour
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetEvents
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetHide
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetModel
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetNpcHead
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetNpcHeadActive
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetObject
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetPlayerHead
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetPlayerModelBaseColour
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetPlayerModelBodyType
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetPlayerModelObj
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetPlayerModelSelf
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetPosition
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetRotateSpeed
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetScrollPos
import net.rsprox.protocol.game.outgoing.model.interfaces.IfSetText
import net.rsprox.protocol.game.outgoing.model.inv.UpdateInvFull
import net.rsprox.protocol.game.outgoing.model.inv.UpdateInvPartial
import net.rsprox.protocol.game.outgoing.model.inv.UpdateInvStopTransmit
import net.rsprox.protocol.game.outgoing.model.logout.Logout
import net.rsprox.protocol.game.outgoing.model.logout.LogoutTransfer
import net.rsprox.protocol.game.outgoing.model.logout.LogoutWithReason
import net.rsprox.protocol.game.outgoing.model.map.RebuildLogin
import net.rsprox.protocol.game.outgoing.model.map.RebuildNormal
import net.rsprox.protocol.game.outgoing.model.map.RebuildRegion
import net.rsprox.protocol.game.outgoing.model.map.RebuildWorldEntity
import net.rsprox.protocol.game.outgoing.model.misc.client.HideLocOps
import net.rsprox.protocol.game.outgoing.model.misc.client.HideNpcOps
import net.rsprox.protocol.game.outgoing.model.misc.client.HidePlayerOps
import net.rsprox.protocol.game.outgoing.model.misc.client.HintArrow
import net.rsprox.protocol.game.outgoing.model.misc.client.HiscoreReply
import net.rsprox.protocol.game.outgoing.model.misc.client.MinimapToggle
import net.rsprox.protocol.game.outgoing.model.misc.client.ReflectionChecker
import net.rsprox.protocol.game.outgoing.model.misc.client.ResetAnims
import net.rsprox.protocol.game.outgoing.model.misc.client.SendPing
import net.rsprox.protocol.game.outgoing.model.misc.client.ServerTickEnd
import net.rsprox.protocol.game.outgoing.model.misc.client.SetHeatmapEnabled
import net.rsprox.protocol.game.outgoing.model.misc.client.SiteSettings
import net.rsprox.protocol.game.outgoing.model.misc.client.UpdateRebootTimer
import net.rsprox.protocol.game.outgoing.model.misc.client.UpdateUid192
import net.rsprox.protocol.game.outgoing.model.misc.client.UrlOpen
import net.rsprox.protocol.game.outgoing.model.misc.player.ChatFilterSettings
import net.rsprox.protocol.game.outgoing.model.misc.player.ChatFilterSettingsPrivateChat
import net.rsprox.protocol.game.outgoing.model.misc.player.MessageGame
import net.rsprox.protocol.game.outgoing.model.misc.player.RunClientScript
import net.rsprox.protocol.game.outgoing.model.misc.player.SetMapFlag
import net.rsprox.protocol.game.outgoing.model.misc.player.SetPlayerOp
import net.rsprox.protocol.game.outgoing.model.misc.player.TriggerOnDialogAbort
import net.rsprox.protocol.game.outgoing.model.misc.player.UpdateRunEnergy
import net.rsprox.protocol.game.outgoing.model.misc.player.UpdateRunWeight
import net.rsprox.protocol.game.outgoing.model.misc.player.UpdateStat
import net.rsprox.protocol.game.outgoing.model.misc.player.UpdateStatOld
import net.rsprox.protocol.game.outgoing.model.misc.player.UpdateStockMarketSlot
import net.rsprox.protocol.game.outgoing.model.misc.player.UpdateTradingPost
import net.rsprox.protocol.game.outgoing.model.social.FriendListLoaded
import net.rsprox.protocol.game.outgoing.model.social.MessagePrivate
import net.rsprox.protocol.game.outgoing.model.social.MessagePrivateEcho
import net.rsprox.protocol.game.outgoing.model.social.UpdateFriendList
import net.rsprox.protocol.game.outgoing.model.social.UpdateIgnoreList
import net.rsprox.protocol.game.outgoing.model.sound.MidiJingle
import net.rsprox.protocol.game.outgoing.model.sound.MidiSong
import net.rsprox.protocol.game.outgoing.model.sound.MidiSongOld
import net.rsprox.protocol.game.outgoing.model.sound.MidiSongStop
import net.rsprox.protocol.game.outgoing.model.sound.MidiSongWithSecondary
import net.rsprox.protocol.game.outgoing.model.sound.MidiSwap
import net.rsprox.protocol.game.outgoing.model.sound.SynthSound
import net.rsprox.protocol.game.outgoing.model.specific.LocAnimSpecific
import net.rsprox.protocol.game.outgoing.model.specific.MapAnimSpecific
import net.rsprox.protocol.game.outgoing.model.specific.NpcAnimSpecific
import net.rsprox.protocol.game.outgoing.model.specific.NpcHeadIconSpecific
import net.rsprox.protocol.game.outgoing.model.specific.NpcSpotAnimSpecific
import net.rsprox.protocol.game.outgoing.model.specific.PlayerAnimSpecific
import net.rsprox.protocol.game.outgoing.model.specific.PlayerSpotAnimSpecific
import net.rsprox.protocol.game.outgoing.model.specific.ProjAnimSpecific
import net.rsprox.protocol.game.outgoing.model.varp.VarpLarge
import net.rsprox.protocol.game.outgoing.model.varp.VarpReset
import net.rsprox.protocol.game.outgoing.model.varp.VarpSmall
import net.rsprox.protocol.game.outgoing.model.varp.VarpSync
import net.rsprox.protocol.game.outgoing.model.worldentity.ClearEntities
import net.rsprox.protocol.game.outgoing.model.worldentity.SetActiveWorld
import net.rsprox.protocol.game.outgoing.model.zone.header.UpdateZoneFullFollows
import net.rsprox.protocol.game.outgoing.model.zone.header.UpdateZonePartialEnclosed
import net.rsprox.protocol.game.outgoing.model.zone.header.UpdateZonePartialFollows
import net.rsprox.protocol.game.outgoing.model.zone.payload.LocAddChange
import net.rsprox.protocol.game.outgoing.model.zone.payload.LocAnim
import net.rsprox.protocol.game.outgoing.model.zone.payload.LocDel
import net.rsprox.protocol.game.outgoing.model.zone.payload.LocMerge
import net.rsprox.protocol.game.outgoing.model.zone.payload.MapAnim
import net.rsprox.protocol.game.outgoing.model.zone.payload.MapProjAnim
import net.rsprox.protocol.game.outgoing.model.zone.payload.ObjAdd
import net.rsprox.protocol.game.outgoing.model.zone.payload.ObjCount
import net.rsprox.protocol.game.outgoing.model.zone.payload.ObjDel
import net.rsprox.protocol.game.outgoing.model.zone.payload.ObjEnabledOps
import net.rsprox.protocol.game.outgoing.model.zone.payload.SoundArea
import net.rsprox.protocol.reflection.ReflectionCheck
import net.rsprox.shared.ScriptVarType
import net.rsprox.shared.property.ChildProperty
import net.rsprox.shared.property.NamedEnum
import net.rsprox.shared.property.Property
import net.rsprox.shared.property.RootProperty
import net.rsprox.shared.property.boolean
import net.rsprox.shared.property.com
import net.rsprox.shared.property.coordGrid
import net.rsprox.shared.property.enum
import net.rsprox.shared.property.filteredBoolean
import net.rsprox.shared.property.filteredInt
import net.rsprox.shared.property.filteredLong
import net.rsprox.shared.property.filteredNamedEnum
import net.rsprox.shared.property.filteredString
import net.rsprox.shared.property.formattedInt
import net.rsprox.shared.property.group
import net.rsprox.shared.property.identifiedNpc
import net.rsprox.shared.property.identifiedPlayer
import net.rsprox.shared.property.identifiedWorldEntity
import net.rsprox.shared.property.int
import net.rsprox.shared.property.inter
import net.rsprox.shared.property.long
import net.rsprox.shared.property.namedEnum
import net.rsprox.shared.property.regular.ScriptVarTypeProperty
import net.rsprox.shared.property.regular.ZoneCoordProperty
import net.rsprox.shared.property.script
import net.rsprox.shared.property.scriptVarType
import net.rsprox.shared.property.string
import net.rsprox.shared.property.unidentifiedNpc
import net.rsprox.shared.property.unidentifiedPlayer
import net.rsprox.shared.property.unidentifiedWorldEntity
import net.rsprox.shared.property.varbit
import net.rsprox.shared.property.varp
import net.rsprox.shared.property.zoneCoordGrid
import net.rsprox.transcriber.base.maxUShortToMinusOne
import net.rsprox.transcriber.impl.ServerPacketTranscriber
import net.rsprox.transcriber.state.Player
import net.rsprox.transcriber.state.StateTracker
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

@Suppress("SpellCheckingInspection", "DuplicatedCode")
public class BaseServerPacketTranscriber(
    private val stateTracker: StateTracker,
    private val cache: Cache,
) : ServerPacketTranscriber {
    private val root: RootProperty<*>
        get() = stateTracker.root

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

    private fun Property.worldentity(
        index: Int,
        name: String = "worldentity",
    ): ChildProperty<*> {
        if (index == -1) {
            return string("worldentity", "root")
        }
        val world = stateTracker.getWorldOrNull(index)
        return if (world != null) {
            identifiedWorldEntity(
                index,
                world.coord.level,
                world.coord.x,
                world.coord.z,
                world.sizeX,
                world.sizeZ,
                name,
            )
        } else {
            unidentifiedWorldEntity(index, name)
        }
    }

    private fun buildAreaCoordGrid(
        xInBuildArea: Int,
        zInBuildArea: Int,
        level: Int = -1,
    ): CoordGrid {
        return stateTracker
            .getActiveWorld()
            .relativizeBuildAreaCoord(
                xInBuildArea,
                zInBuildArea,
                if (level == -1) stateTracker.level() else level,
            )
    }

    private fun Property.coordGrid(coordGrid: CoordGrid): ScriptVarTypeProperty<*> {
        return coordGrid(coordGrid.level, coordGrid.x, coordGrid.z)
    }

    private fun Property.coordGrid(
        name: String,
        coordGrid: CoordGrid,
    ): ScriptVarTypeProperty<*> {
        return coordGrid(coordGrid.level, coordGrid.x, coordGrid.z, name)
    }

    private fun Property.zoneCoord(
        name: String,
        level: Int,
        zoneX: Int,
        zoneZ: Int,
    ): ZoneCoordProperty {
        return zoneCoordGrid(level, zoneX shl 3, zoneZ shl 3, name)
    }

    override fun camLookAt(message: CamLookAt) {
        root.coordGrid(buildAreaCoordGrid(message.destinationXInBuildArea, message.destinationZInBuildArea))
        root.int("height", message.height)
        root.int("rate", message.speed)
        root.int("rate2", message.acceleration)
    }

    override fun camLookAtEasedCoord(message: CamLookAtEasedCoord) {
        root.coordGrid(buildAreaCoordGrid(message.destinationXInBuildArea, message.destinationZInBuildArea))
        root.int("height", message.height)
        root.int("cycles", message.duration)
        root.enum("easing", message.function)
    }

    override fun camMode(message: CamMode) {
        root.int("mode", message.mode)
    }

    override fun camMoveTo(message: CamMoveTo) {
        root.coordGrid(buildAreaCoordGrid(message.destinationXInBuildArea, message.destinationZInBuildArea))
        root.int("height", message.height)
        root.int("rate", message.speed)
        root.int("rate2", message.acceleration)
    }

    override fun camMoveToArc(message: CamMoveToArc) {
        root.coordGrid(buildAreaCoordGrid(message.destinationXInBuildArea, message.destinationZInBuildArea))
        root.int("height", message.height)
        root.coordGrid("tertiarycoord", buildAreaCoordGrid(message.centerXInBuildArea, message.centerZInBuildArea))
        root.int("cycles", message.duration)
        root.boolean("ignoreterrain", message.maintainFixedAltitude)
        root.enum("easing", message.function)
    }

    override fun camMoveToCycles(message: CamMoveToCycles) {
        root.coordGrid(buildAreaCoordGrid(message.destinationXInBuildArea, message.destinationZInBuildArea))
        root.int("height", message.height)
        root.int("cycles", message.duration)
        root.boolean("ignoreterrain", message.maintainFixedAltitude)
        root.enum("easing", message.function)
    }

    override fun camReset(message: CamReset) {
    }

    override fun camRotateBy(message: CamRotateBy) {
        root.int("pitch", message.xAngle)
        root.int("yaw", message.yAngle)
        root.int("cycles", message.duration)
        root.enum("easing", message.function)
    }

    override fun camRotateTo(message: CamRotateTo) {
        root.int("pitch", message.xAngle)
        root.int("yaw", message.yAngle)
        root.int("cycles", message.duration)
        root.enum("easing", message.function)
    }

    override fun camShake(message: CamShake) {
        root.int("axis", message.type)
        root.int("random", message.randomAmount)
        root.int("amplitude", message.sineAmount)
        root.int("rate", message.sineFrequency)
    }

    override fun camSmoothReset(message: CamSmoothReset) {
        root.int("moveconstantspeed", message.cameraMoveConstantSpeed)
        root.int("moveproportionalspeed", message.cameraMoveProportionalSpeed)
        root.int("lookconstantspeed", message.cameraLookConstantSpeed)
        root.int("lookproportionalspeed", message.cameraLookProportionalSpeed)
    }

    override fun camTarget(message: CamTarget) {
        when (val type = message.type) {
            is CamTarget.NpcCamTarget -> {
                root.npc(type.index)
            }
            is CamTarget.PlayerCamTarget -> {
                root.player(type.index)
            }
            is CamTarget.WorldEntityTarget -> {
                root.worldentity(type.index)
                if (type.cameraLockedPlayerIndex != -1) {
                    root.player(type.index, "cameralockedplayer")
                }
            }
        }
    }

    override fun camTargetOld(message: CamTargetOld) {
        when (val type = message.type) {
            is CamTargetOld.NpcCamTarget -> {
                root.npc(type.index)
            }
            is CamTargetOld.PlayerCamTarget -> {
                root.player(type.index)
            }
            is CamTargetOld.WorldEntityTarget -> {
                root.worldentity(type.index)
            }
        }
    }

    override fun oculusSync(message: OculusSync) {
        root.int("value", message.value)
    }

    override fun clanChannelDelta(message: ClanChannelDelta) {
        root.int("clantype", message.clanType)
        root.long("clanhash", message.clanHash)
        root.long("updatenum", message.updateNum)
        for (event in message.events) {
            when (event) {
                is ClanChannelDelta.ClanChannelDeltaAddUserEvent -> {
                    root.group("ADD_USER") {
                        string("name", event.name)
                        int("world", event.world)
                        int("rank", event.rank)
                    }
                }
                is ClanChannelDelta.ClanChannelDeltaDeleteUserEvent -> {
                    root.group("DEL_USER") {
                        int("memberindex", event.index)
                    }
                }
                is ClanChannelDelta.ClanChannelDeltaUpdateBaseSettingsEvent -> {
                    root.group("UPDATE_BASE_SETTINGS") {
                        filteredString("clanname", event.clanName, null)
                        int("talkrank", event.talkRank)
                        int("kickrank", event.kickRank)
                    }
                }
                is ClanChannelDelta.ClanChannelDeltaUpdateUserDetailsEvent -> {
                    root.group("UPDATE_USER_DETAILS") {
                        int("memberindex", event.index)
                        string("name", event.name)
                        int("rank", event.rank)
                        int("world", event.world)
                    }
                }
                is ClanChannelDelta.ClanChannelDeltaUpdateUserDetailsV2Event -> {
                    root.group("UPDATE_USER_DETAILS") {
                        int("memberindex", event.index)
                        string("name", event.name)
                        int("rank", event.rank)
                        int("world", event.world)
                    }
                }
            }
        }
    }

    override fun clanChannelFull(message: ClanChannelFull) {
        root.int("clantype", message.clanType)
        when (val update = message.update) {
            is ClanChannelFull.ClanChannelFullJoinUpdate -> {
                root.group("DETAILS") {
                    int("flags", update.flags)
                    int("version", update.version)
                    long("clanhash", update.clanHash)
                    long("updatenum", update.updateNum)
                    string("clanname", update.clanName)
                    boolean("discarded", update.discardedBoolean)
                    int("kickrank", update.kickRank)
                    int("talkrank", update.talkRank)
                }
                root.group("MEMBERS") {
                    for (member in update.members) {
                        group {
                            string("name", member.name)
                            int("rank", member.rank)
                            int("world", member.world)
                            boolean("discarded", member.discardedBoolean)
                        }
                    }
                }
            }
            ClanChannelFull.ClanChannelFullLeaveUpdate -> {
            }
        }
    }

    override fun clanSettingsDelta(message: ClanSettingsDelta) {
        root.int("clantype", message.clanType)
        root.long("ownerhash", message.owner)
        root.int("updatenum", message.updateNum)
        root.group("UPDATES") {
            for (update in message.updates) {
                when (update) {
                    is ClanSettingsDelta.ClanSettingsDeltaSetClanOwnerUpdate -> {
                        group("SET_CLAN_OWNER") {
                            int("memberindex", update.index)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaAddBannedUpdate -> {
                        group("ADD_BANNED") {
                            filteredLong("hash", update.hash, 0)
                            filteredString("name", update.name, null)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaAddMemberV1Update -> {
                        group("ADD_MEMBER_V1") {
                            filteredLong("hash", update.hash, 0)
                            filteredString("name", update.name, null)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaAddMemberV2Update -> {
                        group("ADD_MEMBER_V2") {
                            filteredLong("hash", update.hash, 0)
                            filteredString("name", update.name, null)
                            int("joinruneday", update.joinRuneDay)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaBaseSettingsUpdate -> {
                        group("BASE_SETTINGS") {
                            boolean("allowunaffined", update.allowUnaffined)
                            int("talkrank", update.talkRank)
                            int("kickrank", update.kickRank)
                            int("lootsharerank", update.lootshareRank)
                            int("coinsharerank", update.coinshareRank)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaDeleteBannedUpdate -> {
                        group("DELETE_BANNED") {
                            int("memberindex", update.index)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaDeleteMemberUpdate -> {
                        group("DELETE_MEMBER") {
                            int("memberindex", update.index)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetClanNameUpdate -> {
                        group("SET_CLAN_NAME") {
                            string("clanname", update.clanName)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetIntSettingUpdate -> {
                        group("SET_INT_SETTING") {
                            int("id", update.setting)
                            int("value", update.value)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetLongSettingUpdate -> {
                        group("SET_LONG_SETTING") {
                            int("id", update.setting)
                            long("value", update.value)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetMemberExtraInfoUpdate -> {
                        group("SET_MEMBER_EXTRA_INFO") {
                            int("memberindex", update.index)
                            int("value", update.value)
                            int("startbit", update.startBit)
                            int("endbit", update.endBit)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetMemberMutedUpdate -> {
                        group("SET_MEMBER_MUTED") {
                            int("memberindex", update.index)
                            boolean("muted", update.muted)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetMemberRankUpdate -> {
                        group("SET_MEMBER_RANK") {
                            int("memberindex", update.index)
                            int("rank", update.rank)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetStringSettingUpdate -> {
                        group("SET_STRING_SETTING") {
                            int("id", update.setting)
                            string("value", update.value)
                        }
                    }
                    is ClanSettingsDelta.ClanSettingsDeltaSetVarbitSettingUpdate -> {
                        group("SET_VARBIT_SETTING") {
                            int("id", update.setting)
                            int("value", update.value)
                            int("startbit", update.startBit)
                            int("endbit", update.endBit)
                        }
                    }
                }
            }
        }
    }

    private fun formatEpochTimeMinute(num: Int): String {
        val epochTimeMillis = TimeUnit.MINUTES.toMillis(num.toLong())
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(epochTimeMillis))
    }

    override fun clanSettingsFull(message: ClanSettingsFull) {
        root.int("clantype", message.clanType)
        when (val update = message.update) {
            is ClanSettingsFull.ClanSettingsFullJoinUpdate -> {
                root.int("flags", update.flags)
                root.int("updatenum", update.updateNum)
                root.string("creationtime", formatEpochTimeMinute(update.creationTime))
                root.string("clanname", update.clanName)
                root.boolean("allowunaffined", update.allowUnaffined)
                root.int("talkrank", update.talkRank)
                root.int("kickrank", update.kickRank)
                root.filteredInt("lootsharerank", update.lootshareRank, 0)
                root.filteredInt("coinsharerank", update.coinshareRank, 0)
                if (update.affinedMembers.isNotEmpty()) {
                    root.group("AFFINED_MEMBERS") {
                        for (member in update.affinedMembers) {
                            group {
                                filteredLong("hash", member.hash, 0)
                                filteredString("name", member.name, null)
                                int("rank", member.rank)
                                int("extrainfo", member.extraInfo)
                                int("joinruneday", member.joinRuneDay)
                                filteredBoolean("muted", member.muted)
                            }
                        }
                    }
                }
                if (update.bannedMembers.isNotEmpty()) {
                    root.group("BANNED_MEMBERS") {
                        for (member in update.bannedMembers) {
                            group {
                                filteredLong("hash", member.hash, 0)
                                filteredString("name", member.name, null)
                            }
                        }
                    }
                }
                if (update.settings.isNotEmpty()) {
                    root.group("SETTINGS") {
                        for (setting in update.settings) {
                            group {
                                when (setting) {
                                    is ClanSettingsFull.IntClanSetting -> {
                                        int("id", setting.id)
                                        int("int", setting.value)
                                    }
                                    is ClanSettingsFull.LongClanSetting -> {
                                        int("id", setting.id)
                                        long("long", setting.value)
                                    }
                                    is ClanSettingsFull.StringClanSetting -> {
                                        int("id", setting.id)
                                        string("string", setting.value)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ClanSettingsFull.ClanSettingsFullLeaveUpdate -> {}
        }
    }

    override fun messageClanChannel(message: MessageClanChannel) {
        root.int("clantype", message.clanType)
        root.string("name", message.name)
        root.int("world", message.worldId)
        root.int("mescount", message.worldMessageCounter)
        root.filteredInt("chatcrown", message.chatCrownType, 0)
        root.string("message", message.message)
    }

    override fun messageClanChannelSystem(message: MessageClanChannelSystem) {
        root.int("clantype", message.clanType)
        root.int("world", message.worldId)
        root.int("mescount", message.worldMessageCounter)
        root.string("message", message.message)
    }

    override fun varClan(message: VarClan) {
        root.int("id", message.id)
        when (val value = message.value) {
            is VarClan.UnknownVarClanData -> {
                root.string("unknown", value.data.contentToString())
            }
            is VarClan.VarClanIntData -> {
                root.int("int", value.value)
            }
            is VarClan.VarClanLongData -> {
                root.long("long", value.value)
            }
            is VarClan.VarClanStringData -> {
                root.string("string", value.value)
            }
        }
    }

    override fun varClanDisable(message: VarClanDisable) {
    }

    override fun varClanEnable(message: VarClanEnable) {
    }

    override fun messageFriendChannel(message: MessageFriendChannel) {
        root.string("name", message.sender)
        root.string("channelname", message.channelName)
        root.int("world", message.worldId)
        root.int("mescount", message.worldMessageCounter)
        root.int("chatcrown", message.chatCrownType)
        root.string("message", message.message)
    }

    override fun updateFriendChatChannelFullV1(message: UpdateFriendChatChannelFullV1) {
        when (val update = message.updateType) {
            is UpdateFriendChatChannelFullV1.JoinUpdate -> {
                root.string("owner", update.channelOwner)
                root.string("channelname", update.channelName)
                root.int("kickrank", update.kickRank)
                root.group("MEMBERS") {
                    for (member in update.entries) {
                        group {
                            string("name", member.name)
                            int("world", member.worldId)
                            string("worldname", member.worldName)
                            int("rank", member.rank)
                        }
                    }
                }
            }
            UpdateFriendChatChannelFullV1.LeaveUpdate -> {
            }
        }
    }

    override fun updateFriendChatChannelFullV2(message: UpdateFriendChatChannelFullV2) {
        when (val update = message.updateType) {
            is UpdateFriendChatChannelFullV2.JoinUpdate -> {
                root.string("owner", update.channelOwner)
                root.string("channelname", update.channelName)
                root.int("kickrank", update.kickRank)
                root.group("MEMBERS") {
                    for (member in update.entries) {
                        group {
                            string("name", member.name)
                            int("world", member.worldId)
                            string("worldname", member.worldName)
                            int("rank", member.rank)
                        }
                    }
                }
            }
            UpdateFriendChatChannelFullV2.LeaveUpdate -> {
            }
        }
    }

    override fun updateFriendChatChannelSingleUser(message: UpdateFriendChatChannelSingleUser) {
        when (val user = message.user) {
            is UpdateFriendChatChannelSingleUser.AddedFriendChatUser -> {
                root.string("type", "add")
                root.string("name", user.name)
                root.int("world", user.worldId)
                root.string("worldname", user.worldName)
                root.int("rank", user.rank)
            }
            is UpdateFriendChatChannelSingleUser.RemovedFriendChatUser -> {
                root.string("type", "del")
                root.string("name", user.name)
                root.int("world", user.worldId)
                root.int("rank", user.rank)
            }
        }
    }

    override fun setNpcUpdateOrigin(message: SetNpcUpdateOrigin) {
        root.coordGrid(buildAreaCoordGrid(message.originX, message.originZ))
    }

    private fun preWorldEntityUpdate(message: WorldEntityInfo) {
        for ((index, update) in message.updates) {
            when (update) {
                is WorldEntityUpdateType.Active -> {
                }
                WorldEntityUpdateType.HighResolutionToLowResolution -> {
                }
                is WorldEntityUpdateType.LowResolutionToHighResolution -> {
                    val world = stateTracker.createWorld(index)
                    world.sizeX = update.sizeX
                    world.sizeZ = update.sizeZ
                    world.angle = update.angle
                    world.unknownProperty = update.unknownProperty
                    world.coord = update.coordGrid
                }
                WorldEntityUpdateType.Idle -> {
                    // noop
                }
            }
        }
    }

    private fun postWorldEntityUpdate(message: WorldEntityInfo) {
        for ((index, update) in message.updates) {
            when (update) {
                is WorldEntityUpdateType.Active -> {
                    val world = stateTracker.getWorld(index)
                    world.angle = update.angle
                    world.coord = update.coordGrid
                    world.moveSpeed = update.moveSpeed
                }
                WorldEntityUpdateType.HighResolutionToLowResolution -> {
                    stateTracker.destroyWorld(index)
                }
                is WorldEntityUpdateType.LowResolutionToHighResolution -> {
                }
                WorldEntityUpdateType.Idle -> {
                    // noop
                }
            }
        }
    }

    override fun worldEntityInfo(message: WorldEntityInfo) {
        preWorldEntityUpdate(message)
        val group =
            root.group {
                for ((index, update) in message.updates) {
                    when (update) {
                        is WorldEntityUpdateType.Active -> {
                            group("ACTIVE") {
                                worldentity(index)
                                int("angle", update.angle)
                                string("movespeed", "${update.moveSpeed.id * 0.5} tiles/gamecycle")
                                coordGrid("newcoord", update.coordGrid)
                            }
                        }
                        WorldEntityUpdateType.HighResolutionToLowResolution -> {
                            group("DEL") {
                                worldentity(index)
                            }
                        }
                        WorldEntityUpdateType.Idle -> {
                            // noop
                        }
                        is WorldEntityUpdateType.LowResolutionToHighResolution -> {
                            group("ADD") {
                                worldentity(index)
                                int("angle", update.angle)
                                int("unknown", update.unknownProperty)
                            }
                        }
                    }
                }
            }
        postWorldEntityUpdate(message)
        val children = group.children
        // If no children were added to the root group, it means no worldentities are being updated
        // In this case, remove the empty line that the group is generating
        if (children.isEmpty()) {
            root.children.clear()
            return
        }
        // Remove the empty line generated by the wrapper group
        root.children.clear()
        root.children.addAll(children)
    }

    override fun ifClearInv(message: IfClearInv) {
        root.com(message.interfaceId, message.componentId)
    }

    override fun ifCloseSub(message: IfCloseSub) {
        val interfaceId = stateTracker.getOpenInterface(message.combinedId)
        stateTracker.closeInterface(message.combinedId)
        root.com(message.interfaceId, message.componentId)
        if (interfaceId != null) {
            root.inter(interfaceId)
        }
    }

    override fun ifMoveSub(message: IfMoveSub) {
        val interfaceId = stateTracker.getOpenInterface(message.sourceCombinedId)
        stateTracker.moveInterface(message.sourceCombinedId, message.destinationCombinedId)
        root.com("sourcecom", message.sourceInterfaceId, message.sourceComponentId)
        root.com("destcom", message.destinationInterfaceId, message.destinationComponentId)
        if (interfaceId != null) {
            root.inter(interfaceId)
        }
    }

    private enum class IfType(
        override val prettyName: String,
    ) : NamedEnum {
        MODAL("modal"),
        OVERLAY("overlay"),
        CLIENT("client"),
    }

    private fun getIfType(id: Int): IfType {
        return when (id) {
            0 -> IfType.MODAL
            1 -> IfType.OVERLAY
            3 -> IfType.CLIENT
            else -> error("Unknown type: $id")
        }
    }

    override fun ifOpenSub(message: IfOpenSub) {
        root.com(message.destinationInterfaceId, message.destinationComponentId)
        root.inter(message.interfaceId)
        root.namedEnum("type", getIfType(message.type))
    }

    override fun ifOpenTop(message: IfOpenTop) {
        val existing = stateTracker.toplevelInterface
        stateTracker.toplevelInterface = message.interfaceId
        if (existing != -1) {
            root.inter("previousid", existing)
        }
        root.inter(message.interfaceId)
    }

    private enum class EventMask(
        val mask: Int,
    ) {
        PAUSEBUTTON(1 shl 0),
        OP1(1 shl 1),
        OP2(1 shl 2),
        OP3(1 shl 3),
        OP4(1 shl 4),
        OP5(1 shl 5),
        OP6(1 shl 6),
        OP7(1 shl 7),
        OP8(1 shl 8),
        OP9(1 shl 9),
        OP10(1 shl 10),
        TGTOBJ(1 shl 11),
        TGTNPC(1 shl 12),
        TGTLOC(1 shl 13),
        TGTPLAYER(1 shl 14),
        TGTINV(1 shl 15),
        TGTCOM(1 shl 16),
        DEPTH1(1 shl 17),
        DEPTH2(2 shl 17),
        DEPTH3(3 shl 17),
        DEPTH4(4 shl 17),
        DEPTH5(5 shl 17),
        DEPTH6(6 shl 17),
        DEPTH7(7 shl 17),
        DRAGTARGET(1 shl 20),
        TARGET(1 shl 21),
        CRMTARGET(1 shl 22),
        BIT23(1 shl 23),
        BIT24(1 shl 24),
        BIT25(1 shl 25),
        BIT26(1 shl 26),
        BIT27(1 shl 27),
        BIT28(1 shl 28),
        BIT29(1 shl 29),
        BIT30(1 shl 30),
        BIT31(1 shl 31),
        ;

        companion object {
            fun list(mask: Int): List<EventMask> {
                return buildList {
                    for (entry in entries) {
                        if (mask and entry.mask != entry.mask) {
                            continue
                        }
                        add(entry)
                    }
                }
            }
        }
    }

    override fun ifResync(message: IfResync) {
        stateTracker.toplevelInterface = message.topLevelInterface
        root.inter(message.topLevelInterface)
        root.group("SUB_INTERFACES") {
            for (sub in message.subInterfaces) {
                stateTracker.openInterface(sub.interfaceId, sub.destinationCombinedId)
                group {
                    com(sub.destinationInterfaceId, sub.destinationComponentId)
                    inter(sub.interfaceId)
                    namedEnum("type", getIfType(sub.type))
                }
            }
        }
        root.group("EVENTS") {
            for (event in message.events) {
                group {
                    com(event.interfaceId, event.componentId)
                    int("start", event.start.maxUShortToMinusOne())
                    int("end", event.end.maxUShortToMinusOne())
                    string("events", EventMask.list(event.events).toString())
                }
            }
        }
    }

    override fun ifSetAngle(message: IfSetAngle) {
        root.com(message.interfaceId, message.componentId)
        root.int("anglex", message.angleX)
        root.int("angley", message.angleY)
        root.int("zoom", message.zoom)
    }

    override fun ifSetAnim(message: IfSetAnim) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.SEQ, message.anim)
    }

    override fun ifSetColour(message: IfSetColour) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("colour", ScriptVarType.COLOUR, message.colour15BitPacked)
    }

    override fun ifSetEvents(message: IfSetEvents) {
        root.com(message.interfaceId, message.componentId)
        root.int("start", message.start.maxUShortToMinusOne())
        root.int("end", message.start.maxUShortToMinusOne())
        root.string("events", EventMask.list(message.events).toString())
    }

    override fun ifSetHide(message: IfSetHide) {
        root.com(message.interfaceId, message.componentId)
        root.boolean("hide", message.hidden)
    }

    override fun ifSetModel(message: IfSetModel) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.MODEL, message.model)
    }

    override fun ifSetNpcHead(message: IfSetNpcHead) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.NPC, message.npc)
    }

    override fun ifSetNpcHeadActive(message: IfSetNpcHeadActive) {
        root.com(message.interfaceId, message.componentId)
        root.npc(message.index)
    }

    override fun ifSetObject(message: IfSetObject) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.OBJ, message.obj)
        root.int("zoomorcount", message.count)
    }

    override fun ifSetPlayerHead(message: IfSetPlayerHead) {
        root.com(message.interfaceId, message.componentId)
    }

    override fun ifSetPlayerModelBaseColour(message: IfSetPlayerModelBaseColour) {
        root.com(message.interfaceId, message.componentId)
        root.int("index", message.index)
        root.scriptVarType("colour", ScriptVarType.COLOUR, message.colour)
    }

    override fun ifSetPlayerModelBodyType(message: IfSetPlayerModelBodyType) {
        root.com(message.interfaceId, message.componentId)
        root.int("bodytype", message.bodyType)
    }

    override fun ifSetPlayerModelObj(message: IfSetPlayerModelObj) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.OBJ, message.obj)
    }

    override fun ifSetPlayerModelSelf(message: IfSetPlayerModelSelf) {
        root.com(message.interfaceId, message.componentId)
        root.boolean("copyobjs", message.copyObjs)
    }

    override fun ifSetPosition(message: IfSetPosition) {
        root.com(message.interfaceId, message.componentId)
        root.int("x", message.x)
        root.int("y", message.y)
    }

    override fun ifSetRotateSpeed(message: IfSetRotateSpeed) {
        root.com(message.interfaceId, message.componentId)
        root.int("xspeed", message.xSpeed)
        root.int("yspeed", message.ySpeed)
    }

    override fun ifSetScrollPos(message: IfSetScrollPos) {
        root.com(message.interfaceId, message.componentId)
        root.int("scrollpos", message.scrollPos)
    }

    override fun ifSetText(message: IfSetText) {
        root.com(message.interfaceId, message.componentId)
        root.string("text", message.text)
    }

    override fun updateInvFull(message: UpdateInvFull) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.INV, message.inventoryId)
        root.group("OBJS") {
            for (obj in message.objs) {
                group {
                    scriptVarType("id", ScriptVarType.OBJ, obj.id)
                    formattedInt("count", obj.count)
                }
            }
        }
    }

    override fun updateInvPartial(message: UpdateInvPartial) {
        root.com(message.interfaceId, message.componentId)
        root.scriptVarType("id", ScriptVarType.INV, message.inventoryId)
        root.group("OBJS") {
            for (obj in message.objs) {
                group {
                    int("slot", obj.slot)
                    scriptVarType("id", ScriptVarType.OBJ, obj.id)
                    formattedInt("count", obj.count)
                }
            }
        }
    }

    override fun updateInvStopTransmit(message: UpdateInvStopTransmit) {
        root.scriptVarType("id", ScriptVarType.INV, message.inventoryId)
    }

    override fun logout(message: Logout) {
    }

    private enum class WorldFlag(
        val mask: Int,
    ) {
        MEMBERS(0x1),
        QUICKCHAT(0x2),
        PVPWORLD(0x4),
        LOOTSHARE(0x8),
        DEDICATEDACTIVITY(0x10),
        BOUNTYWORLD(0x20),
        PVPARENA(0x40),
        HIGHLEVELONLY_1500(0x80),
        SPEEDRUN(0x100),
        EXISTINGPLAYERSONLY(0x200),
        EXTRAHARDWILDERNESS(0x400),
        DUNGEONEERING(0x800),
        INSTANCE_SHARD(0x1000),
        RENTABLE(0x2000),
        LASTMANSTANDING(0x4000),
        NEW_PLAYERS(0x8000),
        BETA_WORLD(0x10000),
        STAFF_IP_ONLY(0x20000),
        HIGHLEVELONLY_2000(0x40000),
        HIGHLEVELONLY_2400(0x80000),
        VIPS_ONLY(0x100000),
        HIDDEN_WORLD(0x200000),
        LEGACY_ONLY(0x400000),
        EOC_ONLY(0x800000),
        BEHIND_PROXY(0x1000000),
        NOSAVE_MODE(0x2000000),
        TOURNAMENT_WORLD(0x4000000),
        FRESHSTART(0x8000000),
        HIGHLEVELONLY_1750(0x10000000),
        DEADMAN(0x20000000),
        SEASONAL(0x40000000),
        EXTERNAL_PARTNER_ONLY(-0x80000000),
        ;

        companion object {
            fun list(mask: Int): List<WorldFlag> {
                return buildList {
                    for (entry in WorldFlag.entries) {
                        if (mask and entry.mask != entry.mask) {
                            continue
                        }
                        add(entry)
                    }
                }
            }
        }
    }

    override fun logoutTransfer(message: LogoutTransfer) {
        root.string("host", message.host)
        root.int("id", message.id)
        root.string("properties", WorldFlag.list(message.properties).toString())
    }

    private enum class LogoutReason(
        override val prettyName: String,
    ) : NamedEnum {
        REQUESTED("requested"),
        KICKED("kicked"),
        UPDATING("updating"),
    }

    private fun getLogoutReason(id: Int): LogoutReason {
        return when (id) {
            0 -> LogoutReason.REQUESTED
            1 -> LogoutReason.KICKED
            2 -> LogoutReason.UPDATING
            else -> error("Unknown logout reason: $id")
        }
    }

    override fun logoutWithReason(message: LogoutWithReason) {
        root.namedEnum("reason", getLogoutReason(message.reason))
    }

    override fun rebuildLogin(message: RebuildLogin) {
        stateTracker.overridePlayer(
            Player(
                message.playerInfoInitBlock.localPlayerIndex,
                "uninitialized",
                message.playerInfoInitBlock.localPlayerCoord,
            ),
        )
        stateTracker.localPlayerIndex = message.playerInfoInitBlock.localPlayerIndex
        val world = stateTracker.createWorld(-1)
        world.rebuild(CoordGrid(0, (message.zoneX - 6) shl 3, (message.zoneZ - 6) shl 3))
        root.int("zonex", message.zoneX)
        root.int("zonez", message.zoneZ)
        root.int("worldarea", message.worldArea)
        root.coordGrid("localplayercoord", message.playerInfoInitBlock.localPlayerCoord)
        val minMapsquareX = (message.zoneX - 6) ushr 3
        val maxMapsquareX = (message.zoneX + 6) ushr 3
        val minMapsquareZ = (message.zoneZ - 6) ushr 3
        val maxMapsquareZ = (message.zoneZ + 6) ushr 3
        val iterator = message.keys.listIterator()
        root.group("KEYS") {
            for (mapsquareX in minMapsquareX..maxMapsquareX) {
                for (mapsquareZ in minMapsquareZ..maxMapsquareZ) {
                    val mapsquareId = (mapsquareX shl 8) or mapsquareZ
                    val key = iterator.next()
                    group {
                        int("mapsquareid", mapsquareId)
                        string("key", key.key.contentToString())
                    }
                }
            }
        }
        check(!iterator.hasNext()) {
            "Xtea keys leftover"
        }
    }

    override fun rebuildNormal(message: RebuildNormal) {
        val world = stateTracker.getWorld(-1)
        world.rebuild(CoordGrid(0, (message.zoneX - 6) shl 3, (message.zoneZ - 6) shl 3))
        root.int("zonex", message.zoneX)
        root.int("zonez", message.zoneZ)
        root.int("worldarea", message.worldArea)
        val minMapsquareX = (message.zoneX - 6) ushr 3
        val maxMapsquareX = (message.zoneX + 6) ushr 3
        val minMapsquareZ = (message.zoneZ - 6) ushr 3
        val maxMapsquareZ = (message.zoneZ + 6) ushr 3
        val iterator = message.keys.listIterator()
        root.group("KEYS") {
            for (mapsquareX in minMapsquareX..maxMapsquareX) {
                for (mapsquareZ in minMapsquareZ..maxMapsquareZ) {
                    val mapsquareId = (mapsquareX shl 8) or mapsquareZ
                    val key = iterator.next()
                    group {
                        int("mapsquareid", mapsquareId)
                        string("key", key.key.contentToString())
                    }
                }
            }
        }
        check(!iterator.hasNext()) {
            "Xtea keys leftover"
        }
    }

    override fun rebuildRegion(message: RebuildRegion) {
        val world = stateTracker.getWorld(-1)
        world.rebuild(CoordGrid(0, (message.zoneX - 6) shl 3, (message.zoneZ - 6) shl 3))
        root.int("zonex", message.zoneX)
        root.int("zonez", message.zoneZ)
        root.boolean("reload", message.reload)
        val mapsquares = mutableSetOf<Int>()
        root.group("BUILD_AREA") {
            val startZoneX = message.zoneX - 6
            val startZoneZ = message.zoneZ - 6
            for (level in 0..<4) {
                for (zoneX in startZoneX..(message.zoneX + 6)) {
                    for (zoneZ in startZoneZ..(message.zoneZ + 6)) {
                        val block = message.buildArea[level, zoneX - startZoneX, zoneZ - startZoneZ]
                        // Invalid zone
                        if (block.mapsquareId == 32767) continue
                        mapsquares += block.mapsquareId
                        group {
                            zoneCoord("source", block.level, block.zoneX, block.zoneZ)
                            zoneCoord("dest", level, zoneX, zoneZ)
                            int("rotation", block.rotation)
                        }
                    }
                }
            }
        }
        val iterator = message.keys.listIterator()
        root.group("KEYS") {
            for (mapsquareId in mapsquares) {
                val key = iterator.next()
                group {
                    int("mapsquareid", mapsquareId)
                    string("key", key.key.contentToString())
                }
            }
        }
        check(!iterator.hasNext()) {
            "Xtea keys leftover"
        }
    }

    override fun rebuildWorldEntity(message: RebuildWorldEntity) {
        val world = stateTracker.getWorld(-1)
        world.rebuild(CoordGrid(0, (message.baseX - 6) shl 3, (message.baseZ - 6) shl 3))
        root.worldentity(message.index)
        root.int("zonex", message.baseX)
        root.int("zonez", message.baseZ)
        root.coordGrid("localplayercoord", message.playerInfoInitBlock.localPlayerCoord)
        val mapsquares = mutableSetOf<Int>()
        root.group("BUILD_AREA") {
            val startZoneX = message.baseX - 6
            val startZoneZ = message.baseZ - 6
            for (level in 0..<4) {
                for (zoneX in startZoneX..(message.baseX + 6)) {
                    for (zoneZ in startZoneZ..(message.baseZ + 6)) {
                        val block = message.buildArea[level, zoneX - startZoneX, zoneZ - startZoneZ]
                        // Invalid zone
                        if (block.mapsquareId == 32767) continue
                        mapsquares += block.mapsquareId
                        group {
                            zoneCoord("source", block.level, block.zoneX, block.zoneZ)
                            zoneCoord("dest", level, zoneX, zoneZ)
                            int("rotation", block.rotation)
                        }
                    }
                }
            }
        }
        val iterator = message.keys.listIterator()
        root.group("KEYS") {
            for (mapsquareId in mapsquares) {
                val key = iterator.next()
                group {
                    int("mapsquareid", mapsquareId)
                    string("key", key.key.contentToString())
                }
            }
        }
        check(!iterator.hasNext()) {
            "Xtea keys leftover"
        }
    }

    override fun hideLocOps(message: HideLocOps) {
        root.boolean("hide", message.hidden)
    }

    override fun hideNpcOps(message: HideNpcOps) {
        root.boolean("hide", message.hidden)
    }

    override fun hidePlayerOps(message: HidePlayerOps) {
        root.boolean("hide", message.hidden)
    }

    override fun hintArrow(message: HintArrow) {
        when (val type = message.type) {
            is HintArrow.NpcHintArrow -> {
                root.npc(type.index)
            }
            is HintArrow.PlayerHintArrow -> {
                root.player(type.index)
            }
            HintArrow.ResetHintArrow -> {
                root.string("type", "reset")
            }
            is HintArrow.TileHintArrow -> {
                root.coordGrid(stateTracker.level(), type.x, type.z)
                root.int("height", type.height)
                root.string(
                    "position",
                    type.position.name
                        .lowercase()
                        .replaceFirstChar { it.uppercaseChar() },
                )
            }
        }
    }

    override fun hiscoreReply(message: HiscoreReply) {
        root.int("requestid", message.requestId)
        when (val type = message.response) {
            is HiscoreReply.FailedHiscoreReply -> {
                root.string("type", "failure")
                root.string("reason", type.reason)
            }
            is HiscoreReply.SuccessfulHiscoreReply -> {
                root.string("type", "success")
                root.group("OVERALL") {
                    group {
                        int("rank", type.overallRank)
                        long("experience", type.overallExperience)
                    }
                }
                root.group("STATS") {
                    for (stat in type.statResults) {
                        group {
                            namedEnum("stat", Stat.entries.first { it.id == stat.id })
                            int("experience", stat.result)
                            int("rank", stat.rank)
                        }
                    }
                }
                root.group("ACTIVITIES") {
                    for (activity in type.statResults) {
                        group {
                            int("id", activity.id)
                            int("result", activity.result)
                            int("rank", activity.rank)
                        }
                    }
                }
            }
        }
    }

    override fun minimapToggle(message: MinimapToggle) {
        root.int("state", message.minimapState)
    }

    override fun reflectionChecker(message: ReflectionChecker) {
        root.formattedInt("id", message.id)
        root.group("CHECKS") {
            for (check in message.checks) {
                when (check) {
                    is ReflectionCheck.GetFieldModifiers -> {
                        group("GET_FIELD_MODIFIERS") {
                            string("classname", check.className)
                            string("fieldname", check.fieldName)
                        }
                    }
                    is ReflectionCheck.GetFieldValue -> {
                        group("GET_FIELD_VALUE") {
                            string("classname", check.className)
                            string("fieldname", check.fieldName)
                        }
                    }
                    is ReflectionCheck.GetMethodModifiers -> {
                        group("GET_METHOD_MODIFIERS") {
                            string("classname", check.className)
                            string("methodname", check.methodName)
                            string("returnclass", check.returnClass)
                            string("parameterclasses", check.parameterClasses.toString())
                        }
                    }
                    is ReflectionCheck.InvokeMethod -> {
                        group("INVOKE_METHOD") {
                            string("classname", check.className)
                            string("methodname", check.methodName)
                            string("returnclass", check.returnClass)
                            string("parameterclasses", check.parameterClasses.toString())
                            string(
                                "parametervalues",
                                check.parameterValues
                                    .map { it.contentToString() }
                                    .toString(),
                            )
                        }
                    }
                    is ReflectionCheck.SetFieldValue -> {
                        group("SET_FIELD_VALUE") {
                            string("classname", check.className)
                            string("fieldname", check.fieldName)
                            int("value", check.value)
                        }
                    }
                }
            }
        }
    }

    override fun resetAnims(message: ResetAnims) {
    }

    override fun sendPing(message: SendPing) {
        root.int("value1", message.value1)
        root.int("value2", message.value2)
    }

    override fun serverTickEnd(message: ServerTickEnd) {
        stateTracker.incrementCycle()
    }

    override fun setHeatmapEnabled(message: SetHeatmapEnabled) {
        root.boolean("enabled", message.enabled)
    }

    override fun siteSettings(message: SiteSettings) {
        root.string("settings", message.settings)
    }

    override fun updateRebootTimer(message: UpdateRebootTimer) {
        root.formattedInt("gamecycles", message.gameCycles)
    }

    override fun updateUid192(message: UpdateUid192) {
        root.string("uid", message.uid.toString(Charsets.UTF_8))
    }

    override fun urlOpen(message: UrlOpen) {
        root.string("url", message.url)
    }

    private enum class ChatFilter(
        override val prettyName: String,
    ) : NamedEnum {
        ON("on"),
        FRIENDS("friends"),
        OFF("off"),
        HIDE("hide"),
        AUTOCHAT("autochat"),
    }

    private fun getChatFilter(id: Int): ChatFilter {
        return when (id) {
            0 -> ChatFilter.ON
            1 -> ChatFilter.FRIENDS
            2 -> ChatFilter.OFF
            3 -> ChatFilter.HIDE
            4 -> ChatFilter.AUTOCHAT
            else -> error("Unknown chatfilter id: $id")
        }
    }

    override fun chatFilterSettings(message: ChatFilterSettings) {
        root.namedEnum("public", getChatFilter(message.publicChatFilter))
        root.namedEnum("trade", getChatFilter(message.tradeChatFilter))
    }

    override fun chatFilterSettingsPrivateChat(message: ChatFilterSettingsPrivateChat) {
        root.namedEnum("private", getChatFilter(message.privateChatFilter))
    }

    private enum class MessageType(
        val id: Int,
    ) : NamedEnum {
        GAMEMESSAGE(0),
        MODCHAT(1),
        PUBLICCHAT(2),
        PRIVATECHAT(3),
        ENGINE(4),
        LOGINLOGOUTNOTIFICATION(5),
        PRIVATECHATOUT(6),
        MODPRIVATECHAT(7),
        FRIENDSCHAT(9),
        FRIENDSCHATNOTIFICATION(11),
        BROADCAST(14),
        SNAPSHOTFEEDBACK(26),
        OBJ_EXAMINE(27),
        NPC_EXAMINE(28),
        LOC_EXAMINE(29),
        FRIENDNOTIFICATION(30),
        IGNORENOTIFICATION(31),
        CLANCHAT(41),
        CLANMESSAGE(43),
        CLANGUESTCHAT(44),
        CLANGUESTMESSAGE(46),
        AUTOTYPER(90),
        MODAUTOTYPER(91),
        CONSOLE(99),
        TRADEREQ(101),
        TRADE(102),
        CHALREQ_TRADE(103),
        CHALREQ_FRIENDSCHAT(104),
        SPAM(105),
        PLAYERRELATED(106),
        TENSECTIMEOUT(107),
        CLANCREATIONINVITATION(109),
        CLANREQ_CLANCHAT(110),
        DIALOGUE(114),
        MESBOX(115),
        ;

        override val prettyName: String = name.lowercase()
    }

    override fun messageGame(message: MessageGame) {
        val type = MessageType.entries.firstOrNull { it.id == message.type }
        if (type != null) {
            root.namedEnum("type", type)
        } else {
            root.int("type", message.type)
        }
        root.filteredString("name", message.name, null)
        root.string("message", message.message)
    }

    override fun runClientScript(message: RunClientScript) {
        root.script("id", message.id)
        if (message.types.isEmpty() || message.values.isEmpty()) {
            return
        }
        root.group("PARAMS") {
            for (i in message.types.indices) {
                val char = message.types[i]
                val value = message.values[i].toString()
                val type =
                    ScriptVarType.entries.first { type ->
                        type.char == char
                    }
                group {
                    enum("type", type)
                    scriptVarType("value", type, if (type == ScriptVarType.STRING) value else value.toInt())
                }
            }
        }
    }

    override fun setMapFlag(message: SetMapFlag) {
        if (message.xInBuildArea == 0xFF && message.zInBuildArea == 0xFF) {
            root.coordGrid(-1, -1, -1)
        } else {
            root.coordGrid(buildAreaCoordGrid(message.xInBuildArea, message.zInBuildArea))
        }
    }

    override fun setPlayerOp(message: SetPlayerOp) {
        root.int("id", message.id)
        root.string("op", message.op ?: "null")
        root.filteredBoolean("priority", message.priority)
    }

    override fun triggerOnDialogAbort(message: TriggerOnDialogAbort) {
    }

    override fun updateRunEnergy(message: UpdateRunEnergy) {
        root.int("energy", message.runenergy)
    }

    override fun updateRunWeight(message: UpdateRunWeight) {
        root.formattedInt("weight", message.runweight, GRAM_NUMBER_FORMAT)
    }

    private enum class Stat(
        val id: Int,
    ) : NamedEnum {
        ATTACK(0),
        DEFENCE(1),
        STRENGTH(2),
        HITPOINTS(3),
        RANGED(4),
        PRAYER(5),
        MAGIC(6),
        COOKING(7),
        WOODCUTTING(8),
        FLETCHING(9),
        FISHING(10),
        FIREMAKING(11),
        CRAFTING(12),
        SMITHING(13),
        MINING(14),
        HERBLORE(15),
        AGILITY(16),
        THIEVING(17),
        SLAYER(18),
        FARMING(19),
        RUNECRAFTING(20),
        HUNTER(21),
        CONSTRUCTION(22),
        SAILING(23),
        UNRELEASED(24),
        ;

        override val prettyName: String = name.lowercase()
    }

    override fun updateStat(message: UpdateStat) {
        root.namedEnum("stat", Stat.entries.first { it.id == message.stat })
        root.int("level", message.currentLevel)
        root.filteredInt("invisiblelevel", message.invisibleBoostedLevel, message.currentLevel)
        root.formattedInt("experience", message.experience)
    }

    override fun updateStatOld(message: UpdateStatOld) {
        root.namedEnum("stat", Stat.entries.first { it.id == message.stat })
        root.int("level", message.currentLevel)
        root.formattedInt("experience", message.experience)
    }

    override fun updateStockMarketSlot(message: UpdateStockMarketSlot) {
        root.int("slot", message.slot)
        when (val update = message.update) {
            UpdateStockMarketSlot.ResetStockMarketSlot -> {}
            is UpdateStockMarketSlot.SetStockMarketSlot -> {
                root.int("status", update.status)
                root.scriptVarType("id", ScriptVarType.OBJ, update.obj)
                root.formattedInt("price", update.price)
                root.formattedInt("count", update.count)
                root.formattedInt("completedcount", update.completedCount)
                root.formattedInt("completedgold", update.completedGold)
            }
        }
    }

    override fun updateTradingPost(message: UpdateTradingPost) {
        when (val update = message.updateType) {
            UpdateTradingPost.ResetTradingPost -> {}
            is UpdateTradingPost.SetTradingPostOfferList -> {
                root.long("age", update.age)
                root.scriptVarType("id", ScriptVarType.OBJ, update.obj)
                root.boolean("status", update.status)
                root.group("OFFERS") {
                    for (offer in update.offers) {
                        group {
                            string("name", offer.name)
                            string("previousname", offer.previousName)
                            int("world", offer.world)
                            long("time", offer.time)
                            formattedInt("price", offer.price)
                            formattedInt("count", offer.count)
                        }
                    }
                }
            }
        }
    }

    override fun friendListLoaded(message: FriendListLoaded) {
    }

    override fun messagePrivate(message: MessagePrivate) {
        root.string("from", message.sender)
        root.int("world", message.worldId)
        root.int("mescount", message.worldMessageCounter)
        root.filteredInt("chatcrown", message.chatCrownType, 0)
        root.string("message", message.message)
    }

    override fun messagePrivateEcho(message: MessagePrivateEcho) {
        root.string("to", message.recipient)
        root.string("message", message.message)
    }

    private fun formatPlatform(id: Int): String {
        return when (id) {
            0 -> "RuneScape"
            4 -> "RuneScape Lobby"
            8 -> "Old School RuneScape"
            else -> id.toString()
        }
    }

    override fun updateFriendList(message: UpdateFriendList) {
        for (friend in message.friends) {
            when (friend) {
                is UpdateFriendList.OfflineFriend -> {
                    root.group("OFFLINE_FRIEND") {
                        string("name", friend.name)
                        filteredString("previousname", friend.previousName, "")
                        filteredInt("rank", friend.rank, 0)
                        filteredInt("properties", friend.properties, 0)
                        filteredString("notes", friend.notes, "")
                        filteredBoolean("added", friend.added)
                    }
                }
                is UpdateFriendList.OnlineFriend -> {
                    root.group("ONLINE_FRIEND") {
                        string("name", friend.name)
                        filteredString("previousname", friend.previousName, "")
                        int("world", friend.worldId)
                        filteredInt("rank", friend.rank, 0)
                        filteredInt("properties", friend.properties, 0)
                        filteredString("notes", friend.notes, "")
                        string("worldname", friend.worldName)
                        string("platform", formatPlatform(friend.platform))
                        filteredInt("worldflags", friend.worldFlags, 0)
                        filteredBoolean("added", friend.added)
                    }
                }
            }
        }
    }

    override fun updateIgnoreList(message: UpdateIgnoreList) {
        for (ignore in message.ignores) {
            when (ignore) {
                is UpdateIgnoreList.AddedIgnoredEntry -> {
                    root.group("ADDED_IGNORE") {
                        string("name", ignore.name)
                        filteredString("previousname", ignore.previousName, "")
                        filteredString("notes", ignore.note, "")
                        filteredBoolean("added", ignore.added)
                    }
                }
                is UpdateIgnoreList.RemovedIgnoredEntry -> {
                    root.group("REMOVED_IGNORE") {
                        string("name", ignore.name)
                    }
                }
            }
        }
    }

    override fun midiJingle(message: MidiJingle) {
        root.scriptVarType("id", ScriptVarType.JINGLE, message.id)
        if (message.lengthInMillis != 0) {
            root.formattedInt("length", message.lengthInMillis, MS_NUMBER_FORMAT)
        }
    }

    override fun midiSong(message: MidiSong) {
        root.scriptVarType("id", ScriptVarType.MIDI, message.id)
        root.int("fadeoutdelay", message.fadeOutDelay)
        root.int("fadeoutspeed", message.fadeOutSpeed)
        root.int("fadeindelay", message.fadeInDelay)
        root.int("fadeinspeed", message.fadeInSpeed)
    }

    override fun midiSongOld(message: MidiSongOld) {
        root.scriptVarType("id", ScriptVarType.MIDI, message.id)
    }

    override fun midiSongStop(message: MidiSongStop) {
        root.int("fadeoutdelay", message.fadeOutDelay)
        root.int("fadeoutspeed", message.fadeOutSpeed)
    }

    override fun midiSongWithSecondary(message: MidiSongWithSecondary) {
        root.scriptVarType("primaryid", ScriptVarType.MIDI, message.primaryId)
        root.scriptVarType("secondaryid", ScriptVarType.MIDI, message.secondaryId)
        root.int("fadeoutdelay", message.fadeOutDelay)
        root.int("fadeoutspeed", message.fadeOutSpeed)
        root.int("fadeindelay", message.fadeInDelay)
        root.int("fadeinspeed", message.fadeInSpeed)
    }

    override fun midiSwap(message: MidiSwap) {
        root.int("fadeoutdelay", message.fadeOutDelay)
        root.int("fadeoutspeed", message.fadeOutSpeed)
        root.int("fadeindelay", message.fadeInDelay)
        root.int("fadeinspeed", message.fadeInSpeed)
    }

    override fun synthSound(message: SynthSound) {
        root.scriptVarType("id", ScriptVarType.SYNTH, message.id.maxUShortToMinusOne())
        root.filteredInt("loops", message.loops, 1)
        root.filteredInt("delay", message.delay, 0)
    }

    override fun locAnimSpecific(message: LocAnimSpecific) {
        root.scriptVarType("id", ScriptVarType.LOC, message.id)
        root.coordGrid(buildAreaCoordGrid(message.coordInBuildArea.xInBuildArea, message.coordInBuildArea.zInBuildArea))
        root.scriptVarType("shape", ScriptVarType.LOC_SHAPE, message.shape)
        root.int("rotation", message.rotation)
    }

    override fun mapAnimSpecific(message: MapAnimSpecific) {
        root.scriptVarType("id", ScriptVarType.SPOTANIM, message.id)
        root.filteredInt("delay", message.delay, 0)
        root.filteredInt("height", message.height, 0)
        root.coordGrid(buildAreaCoordGrid(message.coordInBuildArea.xInBuildArea, message.coordInBuildArea.zInBuildArea))
    }

    override fun npcAnimSpecific(message: NpcAnimSpecific) {
        root.npc(message.index)
        root.scriptVarType("anim", ScriptVarType.SEQ, message.id)
        root.filteredInt("delay", message.delay, 0)
    }

    override fun npcHeadIconSpecific(message: NpcHeadIconSpecific) {
        root.npc(message.index)
        root.int("slot", message.headIconSlot)
        root.scriptVarType("graphic", ScriptVarType.GRAPHIC, message.spriteGroup)
        root.filteredInt("graphicindex", message.spriteIndex, 0)
    }

    override fun npcSpotAnimSpecific(message: NpcSpotAnimSpecific) {
        root.npc(message.index)
        root.int("slot", message.slot)
        root.scriptVarType("spotanim", ScriptVarType.SPOTANIM, message.id)
        root.filteredInt("height", message.height, 0)
        root.filteredInt("delay", message.delay, 0)
    }

    override fun playerAnimSpecific(message: PlayerAnimSpecific) {
        root.scriptVarType("anim", ScriptVarType.SEQ, message.id)
        root.filteredInt("delay", message.delay, 0)
    }

    override fun playerSpotAnimSpecific(message: PlayerSpotAnimSpecific) {
        root.player(message.index)
        root.int("slot", message.slot)
        root.scriptVarType("spotanim", ScriptVarType.SPOTANIM, message.id)
        root.filteredInt("height", message.height, 0)
        root.filteredInt("delay", message.delay, 0)
    }

    override fun projAnimSpecific(message: ProjAnimSpecific) {
        root.scriptVarType("id", ScriptVarType.SPOTANIM, message.id)
        root.int("starttime", message.startTime)
        root.int("endtime", message.endTime)
        root.int("angle", message.angle)
        root.int("progress", message.progress)
        root.int("startheight", message.startHeight)
        root.int("endheight", message.endHeight)
        root.group("SOURCE") {
            coordGrid(buildAreaCoordGrid(message.coordInBuildArea.xInBuildArea, message.coordInBuildArea.zInBuildArea))
        }
        root.group("TARGET") {
            coordGrid(
                buildAreaCoordGrid(
                    message.coordInBuildArea.xInBuildArea + message.deltaX,
                    message.coordInBuildArea.zInBuildArea + message.deltaZ,
                ),
            )
            val ambiguousIndex = message.targetIndex
            if (ambiguousIndex != 0) {
                if (ambiguousIndex > 0) {
                    npc(ambiguousIndex - 1)
                } else {
                    player(-ambiguousIndex - 1)
                }
            }
        }
    }

    private fun getImpactedVarbits(
        basevar: Int,
        oldValue: Int,
        newValue: Int,
    ): List<VarBitType> {
        if (!stateTracker.varbitsLoaded()) {
            stateTracker.associateVarbits(cache.listVarBitTypes())
        }
        return stateTracker.getAssociatedVarbits(basevar).filter { type ->
            val bitcount = (type.endbit - type.startbit) + 1
            val bitmask = type.bitmask(bitcount)
            val oldVarbitValue = oldValue ushr type.startbit and bitmask
            val newVarbitValue = newValue ushr type.startbit and bitmask
            oldVarbitValue != newVarbitValue
        }
    }

    override fun varpLarge(message: VarpLarge) {
        val oldValue = stateTracker.getVarp(message.id)
        val impactedVarbits = getImpactedVarbits(message.id, oldValue, message.value)
        stateTracker.setVarp(message.id, message.value)
        root.varp("id", message.id)
        root.int("oldvalue", oldValue)
        root.int("newvalue", message.value)
        if (impactedVarbits.isNotEmpty()) {
            for (varbit in impactedVarbits) {
                root.group("VARBIT") {
                    varbit("id", varbit.id)
                    val bitcount = (varbit.endbit - varbit.startbit) + 1
                    val bitmask = varbit.bitmask(bitcount)
                    val oldVarbitValue = oldValue ushr varbit.startbit and bitmask
                    val newVarbitValue = message.value ushr varbit.startbit and bitmask
                    int("oldValue", oldVarbitValue)
                    int("newValue", newVarbitValue)
                }
            }
        }
    }

    override fun varpReset(message: VarpReset) {
    }

    override fun varpSmall(message: VarpSmall) {
        val oldValue = stateTracker.getVarp(message.id)
        val impactedVarbits = getImpactedVarbits(message.id, oldValue, message.value)
        stateTracker.setVarp(message.id, message.value)
        root.varp("id", message.id)
        root.int("oldvalue", oldValue)
        root.int("newvalue", message.value)
        root.group("VARBITS") {
            for (varbit in impactedVarbits) {
                group {
                    varbit("id", varbit.id)
                    val bitcount = (varbit.endbit - varbit.startbit) + 1
                    val bitmask = varbit.bitmask(bitcount)
                    val oldVarbitValue = oldValue ushr varbit.startbit and bitmask
                    val newVarbitValue = message.value ushr varbit.startbit and bitmask
                    int("oldValue", oldVarbitValue)
                    int("newValue", newVarbitValue)
                }
            }
        }
    }

    override fun varpSync(message: VarpSync) {
    }

    override fun clearEntities(message: ClearEntities) {
        stateTracker.destroyDynamicWorlds()
    }

    override fun setActiveWorld(message: SetActiveWorld) {
        when (val type = message.worldType) {
            is SetActiveWorld.DynamicWorldType -> {
                root.worldentity(type.index)
                root.int("level", type.activeLevel)
            }
            is SetActiveWorld.RootWorldType -> {
                root.worldentity(-1)
                root.int("level", type.activeLevel)
            }
        }
    }

    override fun updateZoneFullFollows(message: UpdateZoneFullFollows) {
        stateTracker.getActiveWorld().setActiveZone(message.zoneX, message.zoneZ, message.level)
        root.coordGrid(buildAreaCoordGrid(message.zoneX, message.zoneZ, message.level))
    }

    override fun updateZonePartialEnclosed(message: UpdateZonePartialEnclosed) {
        stateTracker.getActiveWorld().setActiveZone(message.zoneX, message.zoneZ, message.level)
        root.coordGrid(buildAreaCoordGrid(message.zoneX, message.zoneZ, message.level))
        if (message.packets.isEmpty()) return
        root.apply {
            for (event in message.packets) {
                when (event) {
                    is LocAddChange -> {
                        group("LOC_ADD_CHANGE") {
                            buildLocAddChange(event)
                        }
                    }
                    is LocAnim -> {
                        group("LOC_ANIM") {
                            buildLocAnim(event)
                        }
                    }
                    is LocDel -> {
                        group("LOC_DEL") {
                            buildLocDel(event)
                        }
                    }
                    is LocMerge -> {
                        group("LOC_MERGE") {
                            buildLocMerge(event)
                        }
                    }
                    is MapAnim -> {
                        group("MAP_ANIM") {
                            buildMapAnim(event)
                        }
                    }
                    is MapProjAnim -> {
                        group("MAP_PROJANIM") {
                            buildMapProjAnim(event)
                        }
                    }
                    is ObjAdd -> {
                        group("OBJ_ADD") {
                            buildObjAdd(event)
                        }
                    }
                    is ObjCount -> {
                        group("OBJ_COUNT") {
                            buildObjCount(event)
                        }
                    }
                    is ObjDel -> {
                        group("OBJ_DEL") {
                            buildObjDel(event)
                        }
                    }
                    is ObjEnabledOps -> {
                        group("OBJ_ENABLED_OPS") {
                            buildObjEnabledOps(event)
                        }
                    }
                    is SoundArea -> {
                        group("SOUND_AREA") {
                            buildSoundArea(event)
                        }
                    }
                }
            }
        }
    }

    override fun updateZonePartialFollows(message: UpdateZonePartialFollows) {
        stateTracker.getActiveWorld().setActiveZone(message.zoneX, message.zoneZ, message.level)
        root.coordGrid(buildAreaCoordGrid(message.zoneX, message.zoneZ, message.level))
    }

    override fun locAddChange(message: LocAddChange) {
        root.buildLocAddChange(message)
    }

    override fun locAnim(message: LocAnim) {
        root.buildLocAnim(message)
    }

    override fun locDel(message: LocDel) {
        root.buildLocDel(message)
    }

    override fun locMerge(message: LocMerge) {
        root.buildLocMerge(message)
    }

    override fun mapAnim(message: MapAnim) {
        root.buildMapAnim(message)
    }

    override fun mapProjAnim(message: MapProjAnim) {
        root.buildMapProjAnim(message)
    }

    override fun objAdd(message: ObjAdd) {
        root.buildObjAdd(message)
    }

    override fun objCount(message: ObjCount) {
        root.buildObjCount(message)
    }

    override fun objDel(message: ObjDel) {
        root.buildObjDel(message)
    }

    override fun objEnabledOps(message: ObjEnabledOps) {
        root.buildObjEnabledOps(message)
    }

    override fun soundArea(message: SoundArea) {
        root.buildSoundArea(message)
    }

    private fun coordInZone(
        xInZone: Int,
        zInZone: Int,
    ): CoordGrid {
        return stateTracker.getActiveWorld().relativizeZoneCoord(xInZone, zInZone)
    }

    private fun Property.buildLocAddChange(message: LocAddChange) {
        scriptVarType("id", ScriptVarType.LOC, message.id)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
        scriptVarType("shape", ScriptVarType.LOC_SHAPE, message.shape)
        int("rotation", message.rotation)
    }

    private fun Property.buildLocAnim(message: LocAnim) {
        coordGrid(coordInZone(message.xInZone, message.zInZone))
        scriptVarType("shape", ScriptVarType.LOC_SHAPE, message.shape)
        int("rotation", message.rotation)
        scriptVarType("anim", ScriptVarType.SEQ, message.id)
    }

    private fun Property.buildLocDel(message: LocDel) {
        coordGrid(coordInZone(message.xInZone, message.zInZone))
        scriptVarType("shape", ScriptVarType.LOC_SHAPE, message.shape)
        int("rotation", message.rotation)
    }

    private fun Property.buildLocMerge(message: LocMerge) {
        scriptVarType("id", ScriptVarType.LOC, message.id)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
        scriptVarType("shape", ScriptVarType.LOC_SHAPE, message.shape)
        int("rotation", message.rotation)
        int("start", message.start)
        int("end", message.end)
        int("minx", message.minX)
        int("maxx", message.maxX)
        int("minz", message.minZ)
        int("maxz", message.maxZ)
    }

    private fun Property.buildMapAnim(message: MapAnim) {
        scriptVarType("id", ScriptVarType.SPOTANIM, message.id)
        filteredInt("delay", message.delay, 0)
        filteredInt("height", message.height, 0)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
    }

    private fun Property.buildMapProjAnim(message: MapProjAnim) {
        scriptVarType("id", ScriptVarType.SPOTANIM, message.id)
        int("starttime", message.startTime)
        int("endtime", message.endTime)
        int("angle", message.angle)
        int("progress", message.progress)
        int("startheight", message.startHeight)
        int("endheight", message.endHeight)
        group("SOURCE") {
            coordGrid(coordInZone(message.xInZone, message.zInZone))
            val ambiguousIndex = message.sourceIndex
            if (ambiguousIndex != 0) {
                if (ambiguousIndex > 0) {
                    npc(ambiguousIndex - 1)
                } else {
                    player(-ambiguousIndex - 1)
                }
            }
        }
        group("TARGET") {
            coordGrid(coordInZone(message.xInZone + message.deltaX, message.zInZone + message.deltaZ))
            val ambiguousIndex = message.targetIndex
            if (ambiguousIndex != 0) {
                if (ambiguousIndex > 0) {
                    npc(ambiguousIndex - 1)
                } else {
                    player(-ambiguousIndex - 1)
                }
            }
        }
    }

    private enum class ObjOwnership(
        override val prettyName: String,
    ) : NamedEnum {
        None("none"),
        Self("self"),
        Other("other"),
        GroupIronman("gim"),
    }

    private fun getObjOwnership(id: Int): ObjOwnership {
        return when (id) {
            0 -> ObjOwnership.None
            1 -> ObjOwnership.Self
            2 -> ObjOwnership.Other
            3 -> ObjOwnership.GroupIronman
            else -> error("Unknown obj ownership type: $id")
        }
    }

    private fun Property.buildObjAdd(message: ObjAdd) {
        scriptVarType("id", ScriptVarType.OBJ, message.id)
        formattedInt("count", message.quantity)
        filteredString("opflags", "0b" + message.opFlags.value.toString(2), "0b11111")
        filteredInt("reveal", message.timeUntilPublic, 0)
        filteredInt("despawn", message.timeUntilDespawn, 0)
        filteredNamedEnum("ownership", getObjOwnership(message.ownershipType), ObjOwnership.None)
        boolean("neverturnpublic", message.neverBecomesPublic)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
    }

    private fun Property.buildObjCount(message: ObjCount) {
        scriptVarType("id", ScriptVarType.OBJ, message.id)
        formattedInt("oldcount", message.oldQuantity)
        formattedInt("newcount", message.newQuantity)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
    }

    private fun Property.buildObjDel(message: ObjDel) {
        scriptVarType("id", ScriptVarType.OBJ, message.id)
        formattedInt("count", message.quantity)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
    }

    private fun Property.buildObjEnabledOps(message: ObjEnabledOps) {
        scriptVarType("id", ScriptVarType.OBJ, message.id)
        string("opflags", "0b" + message.opFlags.value.toString(2))
        coordGrid(coordInZone(message.xInZone, message.zInZone))
    }

    private fun Property.buildSoundArea(message: SoundArea) {
        scriptVarType("id", ScriptVarType.SYNTH, message.id.maxUShortToMinusOne())
        filteredInt("loops", message.loops, 0)
        filteredInt("delay", message.delay, 0)
        int("range", message.radius)
        filteredInt("size", message.size, 0)
        coordGrid(coordInZone(message.xInZone, message.zInZone))
    }

    private companion object {
        private val MS_NUMBER_FORMAT: NumberFormat = DecimalFormat("###,###,###ms")
        private val GRAM_NUMBER_FORMAT: NumberFormat = DecimalFormat("###,###,###g")
    }
}
