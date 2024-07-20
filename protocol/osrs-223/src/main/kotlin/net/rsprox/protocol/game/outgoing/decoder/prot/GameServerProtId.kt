package net.rsprox.protocol.game.outgoing.decoder.prot

internal object GameServerProtId {
    const val REFLECTION_CHECKER = 0
    const val UPDATE_ZONE_FULL_FOLLOWS = 1
    const val LOGOUT = 2
    const val UPDATE_REBOOT_TIMER = 3
    const val CLANSETTINGS_DELTA = 4
    const val IF_SETPLAYERMODEL_BASECOLOUR = 5
    const val SYNTH_SOUND = 6
    const val IF_SETPLAYERMODEL_SELF = 7
    const val OBJ_COUNT = 8
    const val IF_SETEVENTS = 9
    const val UPDATE_INV_STOPTRANSMIT = 10
    const val UPDATE_ZONE_PARTIAL_FOLLOWS = 11
    const val CLANCHANNEL_FULL = 12
    const val IF_SETTEXT = 13
    const val IF_OPENTOP = 14
    const val VARCLAN_DISABLE = 15
    const val MESSAGE_FRIENDCHANNEL = 16
    const val UPDATE_ZONE_PARTIAL_ENCLOSED = 17
    const val MESSAGE_PRIVATE_ECHO = 18
    const val SET_MAP_FLAG = 19
    const val NPC_INFO_SMALL = 20
    const val HISCORE_REPLY = 21
    const val LOC_ANIM = 22
    const val UPDATE_STAT = 23
    const val IF_SETSCROLLPOS = 24
    const val VARP_SMALL = 25
    const val UPDATE_INV_FULL = 26
    const val UPDATE_RUNWEIGHT = 27
    const val SET_NPC_UPDATE_ORIGIN = 28
    const val MIDI_SONG = 29
    const val RUNCLIENTSCRIPT = 30
    const val CLANSETTINGS_FULL = 31
    const val NPC_INFO_LARGE = 32
    const val SET_HEATMAP_ENABLED = 33
    const val URL_OPEN = 34
    const val IF_MOVESUB = 35
    const val CAM_LOOKAT_EASED_COORD = 36
    const val NPC_HEADICON_SPECIFIC = 37
    const val OBJ_ADD = 38
    const val CAM_SHAKE = 39
    const val MESSAGE_CLANCHANNEL = 40
    const val CAM_ROTATEBY = 41
    const val FRIENDLIST_LOADED = 42
    const val LOC_MERGE = 43
    const val VARP_LARGE = 44
    const val PLAYER_INFO = 45
    const val LOC_ANIM_SPECIFIC = 46
    const val CAM_ROTATETO = 47
    const val IF_RESYNC = 48
    const val IF_SETNPCHEAD_ACTIVE = 49
    const val IF_SETROTATESPEED = 50
    const val IF_SETANGLE = 51
    const val UPDATE_FRIENDCHAT_CHANNEL_SINGLEUSER = 52
    const val NPC_ANIM_SPECIFIC = 53
    const val IF_CLEARINV = 54
    const val MINIMAP_TOGGLE = 55
    const val PROJANIM_SPECIFIC_OLD = 56
    const val IF_SETPOSITION = 57
    const val UPDATE_FRIENDLIST = 58
    const val UPDATE_STAT_OLD = 59
    const val UPDATE_IGNORELIST = 60
    const val IF_SETPLAYERHEAD = 61
    const val UPDATE_UID192 = 62
    const val IF_SETMODEL = 63
    const val MIDI_SWAP = 64
    const val SITE_SETTINGS = 65
    const val MAP_PROJANIM = 66
    const val OBJ_ENABLED_OPS = 67
    const val CAM_MOVETO = 68
    const val LOGOUT_WITHREASON = 69
    const val VARCLAN = 70
    const val CAM_MODE = 71
    const val TRIGGER_ONDIALOGABORT = 72
    const val OCULUS_SYNC = 73
    const val SEND_PING = 74
    const val IF_SETPLAYERMODEL_BODYTYPE = 75
    const val MAP_ANIM_SPECIFIC = 76
    const val MIDI_SONG_STOP = 77
    const val MAP_ANIM = 78
    const val VARP_SYNC = 79
    const val MIDI_SONG_WITHSECONDARY = 80
    const val UPDATE_TRADINGPOST = 81
    const val CHAT_FILTER_SETTINGS_PRIVATECHAT = 82
    const val MESSAGE_PRIVATE = 83
    const val MIDI_SONG_OLD = 84
    const val UPDATE_STOCKMARKET_SLOT = 85
    const val IF_SETCOLOUR = 86
    const val LOC_DEL = 87
    const val MESSAGE_CLANCHANNEL_SYSTEM = 88
    const val IF_SETPLAYERMODEL_OBJ = 89
    const val CAM_RESET = 90
    const val SOUND_AREA = 91
    const val MIDI_JINGLE = 92
    const val CLANCHANNEL_DELTA = 93
    const val CAM_SMOOTH_RESET = 94
    const val MESSAGE_GAME = 95
    const val IF_CLOSESUB = 96
    const val RESET_ANIMS = 97
    const val REBUILD_NORMAL = 98
    const val CAM_LOOKAT = 99
    const val IF_SETNPCHEAD = 100
    const val UPDATE_INV_PARTIAL = 101
    const val IF_SETANIM = 102
    const val OBJ_DEL = 103
    const val LOC_ADD_CHANGE = 104
    const val IF_SETOBJECT = 105
    const val SERVER_TICK_END = 106
    const val UPDATE_RUNENERGY = 107
    const val IF_OPENSUB = 108
    const val CHAT_FILTER_SETTINGS = 109
    const val WORLDENTITY_INFO = 110
    const val VARP_RESET = 111
    const val UPDATE_FRIENDCHAT_CHANNEL_FULL_V2 = 112
    const val LOGOUT_TRANSFER = 113
    const val UPDATE_FRIENDCHAT_CHANNEL_FULL_V1 = 114
    const val NPC_SPOTANIM_SPECIFIC = 115
    const val PLAYER_SPOTANIM_SPECIFIC = 116
    const val PLAYER_ANIM_SPECIFIC = 117
    const val PROJANIM_SPECIFIC = 118
    const val SET_PLAYER_OP = 119
    const val CAM_MOVETO_CYCLES = 120
    const val REBUILD_REGION = 121
    const val IF_SETHIDE = 122
    const val CAM_MOVETO_ARC = 123
    const val VARCLAN_ENABLE = 124
    const val CAM_TARGET = 125
    const val HINT_ARROW = 126
    const val CAM_TARGET_OLD = 127
    const val CLEAR_ENTITIES = 128
    const val HIDENPCOPS = 129
    const val SET_ACTIVE_WORLD = 130
    const val HIDEPLAYEROPS = 131
    const val HIDELOCOPS = 132
    const val REBUILD_WORLDENTITY = 133
}
