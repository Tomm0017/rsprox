@file:Suppress("ktlint:standard:no-wildcard-imports")

package net.rsprox.shared.filters

import net.rsprox.shared.StreamDirection
import net.rsprox.shared.StreamDirection.CLIENT_TO_SERVER
import net.rsprox.shared.StreamDirection.SERVER_TO_CLIENT
import net.rsprox.shared.filters.ProtCategory.*

@Suppress("SpellCheckingInspection")
public enum class PropertyFilter(
    public val direction: StreamDirection,
    public val category: ProtCategory,
    public val label: String,
    public val enabled: Boolean,
    public val tooltip: String? = null,
) {
    IF_BUTTON(CLIENT_TO_SERVER, INTERFACES, "Interface buttons", true),
    IF_BUTTOND(CLIENT_TO_SERVER, INTERFACES, "Interface dragging", false),
    IF_BUTTONT(CLIENT_TO_SERVER, INTERFACES, "Interface targetting", true),

    OPNPC(CLIENT_TO_SERVER, NPCS, "Npc clicks", true),
    OPNPCT(CLIENT_TO_SERVER, NPCS, "Npc targetting", true),

    OPLOC(CLIENT_TO_SERVER, LOCS, "Loc clicks", true),
    OPLOCT(CLIENT_TO_SERVER, LOCS, "Loc targetting", true),

    OPOBJ(CLIENT_TO_SERVER, OBJS, "Obj clicks", true),
    OPOBJT(CLIENT_TO_SERVER, OBJS, "Obj targetting", true),

    OPPLAYER(CLIENT_TO_SERVER, PLAYERS, "Player clicks", true),
    OPPLAYERT(CLIENT_TO_SERVER, PLAYERS, "Player targetting", true),

    EVENT_APPLET_FOCUS(CLIENT_TO_SERVER, EVENTS, "Applet focus", false),
    EVENT_CAMERA_POSITION(CLIENT_TO_SERVER, EVENTS, "Camera position", false),
    EVENT_KEYBOARD(CLIENT_TO_SERVER, EVENTS, "Keyboard", false),
    EVENT_MOUSE_SCROLL(CLIENT_TO_SERVER, EVENTS, "Mouse scroll", false),
    EVENT_MOUSE_MOVE(CLIENT_TO_SERVER, EVENTS, "Mouse movement", false),
    EVENT_NATIVE_MOUSE_MOVE(CLIENT_TO_SERVER, EVENTS, "Native mouse movement", false),
    EVENT_MOUSE_CLICK(CLIENT_TO_SERVER, EVENTS, "Mouse click", false),
    EVENT_NATIVE_MOUSE_CLICK(CLIENT_TO_SERVER, EVENTS, "Native mouse click", false),

    RESUME_PAUSEBUTTON(CLIENT_TO_SERVER, PROT_RESUME, "Pausebutton", true),
    RESUME_P_NAMEDIALOG(CLIENT_TO_SERVER, PROT_RESUME, "Name dialog", true),
    RESUME_P_STRINGDIALOG(CLIENT_TO_SERVER, PROT_RESUME, "String dialog", true),
    RESUME_P_COUNTDIALOG(CLIENT_TO_SERVER, PROT_RESUME, "Count dialog", true),
    RESUME_P_OBJDIALOG(CLIENT_TO_SERVER, PROT_RESUME, "Obj dialog", true),

    FRIENDCHAT_KICK(CLIENT_TO_SERVER, FRIENDCHAT, "Friend chat kick", false),
    FRIENDCHAT_SETRANK(CLIENT_TO_SERVER, FRIENDCHAT, "Friend chat ranks", false),
    FRIENDCHAT_JOIN_LEAVE(CLIENT_TO_SERVER, FRIENDCHAT, "Friend chat join/leave", false),

    CLANCHANNEL_FULL_REQUEST(CLIENT_TO_SERVER, CLAN, "Clan channel requests", false),
    CLANSETTINGS_FULL_REQUEST(CLIENT_TO_SERVER, CLAN, "Clan settings requests", false),
    CLANCHANNEL_KICKUSER(CLIENT_TO_SERVER, CLAN, "Clan member kicking", false),
    AFFINEDCLANSETTINGS_ADDBANNED_FROMCHANNEL(CLIENT_TO_SERVER, CLAN, "Clan member banning", false),
    AFFINEDCLANSETTINGS_SETMUTED_FROMCHANNEL(CLIENT_TO_SERVER, CLAN, "Clan member muting", false),

    FRIENDLIST_ADD(CLIENT_TO_SERVER, SOCIAL, "Friendlist add", false),
    FRIENDLIST_DEL(CLIENT_TO_SERVER, SOCIAL, "Friendlist delete", false),
    IGNORELIST_ADD(CLIENT_TO_SERVER, SOCIAL, "Ignorelist add", false),
    IGNORELIST_DEL(CLIENT_TO_SERVER, SOCIAL, "Ignorelist delete", false),

    MESSAGE_PUBLIC(CLIENT_TO_SERVER, MESSAGING, "Public messages", true),
    MESSAGE_PRIVATE_CLIENT(CLIENT_TO_SERVER, MESSAGING, "Private messages", false),

    MOVE_GAMECLICK(CLIENT_TO_SERVER, OTHER, "Game movement", false),
    MOVE_MINIMAPCLICK(CLIENT_TO_SERVER, OTHER, "Minimap movement", false),
    CLIENT_CHEAT(CLIENT_TO_SERVER, OTHER, "Commands", false),
    SET_CHATFILTERSETTINGS(CLIENT_TO_SERVER, OTHER, "Chat filters", false),
    CLICKWORLDMAP(CLIENT_TO_SERVER, OTHER, "Worldmap hints", false),
    OCULUS_LEAVE(CLIENT_TO_SERVER, OTHER, "Oculus leaving", false),
    CLOSE_MODAL(CLIENT_TO_SERVER, OTHER, "Modal closing", false),
    TELEPORT(CLIENT_TO_SERVER, OTHER, "Teleport requests", false),
    BUG_REPORT(CLIENT_TO_SERVER, OTHER, "Bug reports", false),
    SEND_SNAPSHOT(CLIENT_TO_SERVER, OTHER, "Player reports", false),
    HISCORE_REQUEST(CLIENT_TO_SERVER, OTHER, "Hiscore requests", false),
    IF_CRMVIEW_CLICK(CLIENT_TO_SERVER, OTHER, "CRM clicks", true),

    CONNECTION_TELEMETRY(CLIENT_TO_SERVER, OTHER, "Connection telemetry", false),
    SEND_PING_REPLY(CLIENT_TO_SERVER, OTHER, "Ping reply", false),
    DETECT_MODIFIED_CLIENT(CLIENT_TO_SERVER, OTHER, "Detect modified client", false),
    REFLECTION_CHECK_REPLY(CLIENT_TO_SERVER, OTHER, "Reflection reply", false),
    NO_TIMEOUT(CLIENT_TO_SERVER, OTHER, "No timeout", false),
    IDLE(CLIENT_TO_SERVER, OTHER, "Idle", false),
    MAP_BUILD_COMPLETE(CLIENT_TO_SERVER, OTHER, "Map build complete", false),
    MEMBERSHIP_PROMOTION_ELIGIBILITY(CLIENT_TO_SERVER, OTHER, "Membership promotion eligibility", false),
    SOUND_JINGLEEND(CLIENT_TO_SERVER, OTHER, "Jingle end", false),
    WINDOW_STATUS(CLIENT_TO_SERVER, OTHER, "Window status", false),

    IF_RESYNC(SERVER_TO_CLIENT, INTERFACES, "Interface resync", false),
    IF_OPENTOP(SERVER_TO_CLIENT, INTERFACES, "Top open", true),
    IF_OPENSUB(SERVER_TO_CLIENT, INTERFACES, "Sub open", true),
    IF_CLOSESUB(SERVER_TO_CLIENT, INTERFACES, "Sub close", true),
    IF_MOVESUB(SERVER_TO_CLIENT, INTERFACES, "Sub move", true),
    IF_CLEARINV(SERVER_TO_CLIENT, INTERFACES, "Clear inventories", true),
    IF_SETEVENTS(SERVER_TO_CLIENT, INTERFACES, "Events", true),
    IF_SETPOSITION(SERVER_TO_CLIENT, INTERFACES, "Position", true),
    IF_SETSCROLLPOS(SERVER_TO_CLIENT, INTERFACES, "Scroll position", true),
    IF_SETROTATESPEED(SERVER_TO_CLIENT, INTERFACES, "Rotate speed", true),
    IF_SETTEXT(SERVER_TO_CLIENT, INTERFACES, "Text", true),
    IF_SETHIDE(SERVER_TO_CLIENT, INTERFACES, "Hide", true),
    IF_SETANGLE(SERVER_TO_CLIENT, INTERFACES, "Angle", true),
    IF_SETOBJECT(SERVER_TO_CLIENT, INTERFACES, "Object", true),
    IF_SETCOLOUR(SERVER_TO_CLIENT, INTERFACES, "Colour", true),
    IF_SETANIM(SERVER_TO_CLIENT, INTERFACES, "Anim", true),
    IF_SETNPCHEAD(SERVER_TO_CLIENT, INTERFACES, "Npc head", true),
    IF_SETNPCHEAD_ACTIVE(SERVER_TO_CLIENT, INTERFACES, "Npc head active", true),
    IF_SETPLAYERHEAD(SERVER_TO_CLIENT, INTERFACES, "Player head", true),
    IF_SETMODEL(SERVER_TO_CLIENT, INTERFACES, "Model", true),
    IF_SETPLAYERMODEL(SERVER_TO_CLIENT, INTERFACES, "Player model", true),

    MIDI_SONG(SERVER_TO_CLIENT, MIDI, "Midi song", true),
    MIDI_SWAP(SERVER_TO_CLIENT, MIDI, "Midi swap", true),
    MIDI_SONG_STOP(SERVER_TO_CLIENT, MIDI, "Midi stop", true),
    MIDI_JINGLE(SERVER_TO_CLIENT, MIDI, "Jingle", true),
    SYNTH_SOUND(SERVER_TO_CLIENT, MIDI, "Synth", true),

    ZONE_HEADER(SERVER_TO_CLIENT, ZONES, "Zone header", false),
    LOC_ADD_CHANGE(SERVER_TO_CLIENT, ZONES, "Loc add change", true),
    LOC_DEL(SERVER_TO_CLIENT, ZONES, "Loc del", true),
    LOC_ANIM(SERVER_TO_CLIENT, ZONES, "Loc anim", true),
    LOC_MERGE(SERVER_TO_CLIENT, ZONES, "Loc merge", true),
    OBJ_ADD(SERVER_TO_CLIENT, ZONES, "Obj add", true),
    OBJ_DEL(SERVER_TO_CLIENT, ZONES, "Obj del", true),
    OBJ_COUNT(SERVER_TO_CLIENT, ZONES, "Obj count", true),
    OBJ_ENABLED_OPS(SERVER_TO_CLIENT, ZONES, "Obj enabled ops", true),
    MAP_ANIM(SERVER_TO_CLIENT, ZONES, "Map anim", true),
    MAP_PROJANIM(SERVER_TO_CLIENT, ZONES, "Map proj anim", true),
    SOUND_AREA(SERVER_TO_CLIENT, ZONES, "Area sound", true),

    PROJANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Projectiles", true),
    MAP_ANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Map anims", true),
    LOC_ANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Loc anims", true),
    NPC_HEADICON_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Npc head icons", true),
    NPC_SPOTANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Npc spotanims", true),
    NPC_ANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Npc anims", true),
    PLAYER_ANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Player anims", true),
    PLAYER_SPOTANIM_SPECIFIC(SERVER_TO_CLIENT, SPECIFIC, "Player spotanims", true),

    PLAYER_INFO(SERVER_TO_CLIENT, INFO, "Player info", true),
    PLAYER_OMIT_INDEX(SERVER_TO_CLIENT, INFO, "Player omit index", false),
    PLAYER_REMOVAL(SERVER_TO_CLIENT, INFO, "Player removal", false),
    PLAYER_INFO_OMIT_NO_EXTENDED_INFO(SERVER_TO_CLIENT, INFO, "Omit players w/o ext. info", true),
    PLAYER_INFO_OMIT_EMPTY(SERVER_TO_CLIENT, INFO, "Omit empty player info", true),
    PLAYER_INFO_LOCAL_PLAYER_ONLY(SERVER_TO_CLIENT, INFO, "Local player only", true),
    NPC_INFO(SERVER_TO_CLIENT, INFO, "Npc info", true),
    NPC_OMIT_INDEX(SERVER_TO_CLIENT, INFO, "Npc omit index", false),
    NPC_REMOVAL(SERVER_TO_CLIENT, INFO, "Npc removal", false),
    NPC_INFO_OMIT_NO_EXTENDED_INFO(SERVER_TO_CLIENT, INFO, "Omit npcs w/o ext. info", true),
    NPC_INFO_OMIT_EMPTY(SERVER_TO_CLIENT, INFO, "Omit empty npc info", true),
    SET_NPC_UPDATE_ORIGIN(SERVER_TO_CLIENT, INFO, "Npc info origin", false),

    CLEAR_ENTITIES(SERVER_TO_CLIENT, INFO, "Clear entities", true),
    SET_ACTIVE_WORLD(SERVER_TO_CLIENT, INFO, "Active world", false),
    WORLDENTITY_INFO(SERVER_TO_CLIENT, INFO, "World entity info", false),

    REBUILD(SERVER_TO_CLIENT, MAP, "Rebuild map", false),

    VARP(SERVER_TO_CLIENT, ProtCategory.VARP, "Varps", true),
    VARBITS(SERVER_TO_CLIENT, ProtCategory.VARP, "Varbits", true),
    OMIT_VARP_FOR_VARBITS(
        SERVER_TO_CLIENT,
        ProtCategory.VARP,
        "Omit varp for varbits",
        true,
        "Omits the varp packet header if all the bit changes can be explained by varbits.",
    ),
    VARP_RESET(SERVER_TO_CLIENT, ProtCategory.VARP, "Varp reset", true),
    VARP_SYNC(SERVER_TO_CLIENT, ProtCategory.VARP, "Varp sync", true),

    CAM_SHAKE(SERVER_TO_CLIENT, CAMERA, "Cam shake", true),
    CAM_RESET(SERVER_TO_CLIENT, CAMERA, "Cam reset", true),
    CAM_MOVETO(SERVER_TO_CLIENT, CAMERA, "Camera movement", true),
    CAM_LOOKAT(SERVER_TO_CLIENT, CAMERA, "Camera turning", true),
    CAM_MODE(SERVER_TO_CLIENT, CAMERA, "Camera mode", true),
    CAM_TARGET(SERVER_TO_CLIENT, CAMERA, "Camera target", false),
    OCULUS_SYNC(SERVER_TO_CLIENT, CAMERA, "Oculys sync", true),

    UPDATE_INV(SERVER_TO_CLIENT, INVENTORIES, "Inventory updates", true),

    MESSAGE_PRIVATE(SERVER_TO_CLIENT, MESSAGING, "Private messages", false),
    FRIENDLIST_LOADED(SERVER_TO_CLIENT, SOCIAL, "Friendlist loading", false),
    UPDATE_FRIENDLIST(SERVER_TO_CLIENT, SOCIAL, "Friendlist updates", false),
    UPDATE_IGNORELIST(SERVER_TO_CLIENT, SOCIAL, "Ignorelist updates", false),

    UPDATE_FRIENDCHAT_CHANNEL_FULL(SERVER_TO_CLIENT, FRIENDCHAT, "Friendchat full update", false),
    UPDATE_FRIENDCHAT_CHANNEL_SINGLEUSER(SERVER_TO_CLIENT, FRIENDCHAT, "Friendchat single update", false),
    MESSAGE_FRIENDCHANNEL(SERVER_TO_CLIENT, FRIENDCHAT, "Friendchat messages", false),

    VARCLAN(SERVER_TO_CLIENT, CLAN, "Clan vars", false),
    CLANCHANNEL(SERVER_TO_CLIENT, CLAN, "Clan channel updates", false),
    CLANSETTINGS(SERVER_TO_CLIENT, CLAN, "Clan settings updates", false),
    MESSAGE_CLANCHANNEL(SERVER_TO_CLIENT, CLAN, "Clan messages", false),

    LOGOUT(SERVER_TO_CLIENT, OTHER, "Logout", true),

    UPDATE_RUNWEIGHT(SERVER_TO_CLIENT, OTHER, "Weight", false),
    UPDATE_RUNENERGY(SERVER_TO_CLIENT, OTHER, "Run energy", false),
    SET_MAP_FLAG(SERVER_TO_CLIENT, OTHER, "Minimap flag", false),
    SET_PLAYER_OP(SERVER_TO_CLIENT, OTHER, "Player menu ops", true),
    UPDATE_STAT(SERVER_TO_CLIENT, OTHER, "Stat updates", true),

    RUNCLIENTSCRIPT(SERVER_TO_CLIENT, OTHER, "Clientscripts", true),
    TRIGGER_ONDIALOGABORT(SERVER_TO_CLIENT, OTHER, "Dialog abort", false),
    MESSAGE_GAME(SERVER_TO_CLIENT, MESSAGING, "Game messages", true),
    CHAT_FILTER_SETTINGS(SERVER_TO_CLIENT, OTHER, "Chat filter settings", false),
    UPDATE_STOCKMARKET_SLOT(SERVER_TO_CLIENT, OTHER, "Grand exchange", true),

    HINT_ARROW(SERVER_TO_CLIENT, OTHER, "Hint arrows", true),
    RESET_ANIMS(SERVER_TO_CLIENT, OTHER, "Reset anims", false),
    UPDATE_REBOOT_TIMER(SERVER_TO_CLIENT, OTHER, "Reboot timer", true),
    SET_HEATMAP_ENABLED(SERVER_TO_CLIENT, OTHER, "Heatmap", false),
    MINIMAP_TOGGLE(SERVER_TO_CLIENT, OTHER, "Minimap toggle", true),
    SERVER_TICK_END(SERVER_TO_CLIENT, OTHER, "Server tick end", false),
    HIDEOPS(SERVER_TO_CLIENT, OTHER, "Hide entity ops", true),

    URL_OPEN(SERVER_TO_CLIENT, OTHER, "Url open", false),
    SITE_SETTINGS(SERVER_TO_CLIENT, OTHER, "Site settings", false),
    UPDATE_UID192(SERVER_TO_CLIENT, OTHER, "Uid 192", false),
    REFLECTION_CHECKER(SERVER_TO_CLIENT, OTHER, "Reflection checks", false),
    SEND_PING(SERVER_TO_CLIENT, OTHER, "Ping", false),
    HISCORE_REPLY(SERVER_TO_CLIENT, OTHER, "Hiscore reply", false),

    PLAYER_CHAT(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Chat", false),
    PLAYER_FACE_ANGLE(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Face angle", true),
    PLAYER_MOVE_SPEED(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Move speed", false),
    PLAYER_NAME_EXTRAS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Name extras", false),
    PLAYER_SAY(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Say", true),
    PLAYER_SEQUENCE(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Sequence", true),
    PLAYER_EXACTMOVE(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Exact move", true),
    PLAYER_HITS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Hits & headbars", true),
    PLAYER_TINTING(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Tinting", true),
    PLAYER_SPOTANIMS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Spotanims", true),
    PLAYER_FACE_PATHINGENTITY(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Face pathingentity", true),
    PLAYER_APPEARANCE(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance", true),
    PLAYER_APPEARANCE_DETAILS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Details", true),
    PLAYER_APPEARANCE_STATUS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Status", false),
    PLAYER_APPEARANCE_EQUIPMENT(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Equipment", false),
    PLAYER_APPEARANCE_IDENTKIT(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Identkit", false),
    PLAYER_APPEARANCE_IF_IDENTKIT(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Interface identkit", false),
    PLAYER_APPEARANCE_COLOURS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Colours", false),
    PLAYER_APPEARANCE_BAS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Base anim set", false),
    PLAYER_APPEARANCE_NAME_EXTRAS(SERVER_TO_CLIENT, PLAYER_EXTENDED_INFO, "Appearance: Name extras", false),
    PLAYER_APPEARANCE_OBJ_TYPE_CUSTOMIZATION(
        SERVER_TO_CLIENT,
        PLAYER_EXTENDED_INFO,
        "Appearance: Obj type cust.",
        false,
    ),

    NPC_EXACTMOVE(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Exact move", true),
    NPC_FACE_PATHINGENTITY(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Face pathingentity", true),
    NPC_HITS(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Hits & headbars", true),
    NPC_SAY(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Say", true),
    NPC_SEQUENCE(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Sequence", true),
    NPC_TINTING(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Tinting", true),
    NPC_SPOTANIMS(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Spotanims", true),
    NPC_BAS(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Base animation set", true),
    NPC_BODY_CUSTOMISATION(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Body customisation", true),
    NPC_HEAD_CUSTOMISATION(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Head customisation", true),
    NPC_LEVEL_CHANGE(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Combat level change", true),
    NPC_ENABLED_OPS(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Enabled ops", true),
    NPC_FACE_COORD(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Face coord", true),
    NPC_NAME_CHANGE(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Name change", true),
    NPC_TRANSFORMATION(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Transformation", true),
    NPC_HEADICON_CUSTOMISATION(SERVER_TO_CLIENT, NPC_EXTENDED_INFO, "Headicon customisation", true),

    DEPRECATED_CLIENT(CLIENT_TO_SERVER, DEPRECATED, "Deprecated", true),
    DEPRECATED_SERVER(SERVER_TO_CLIENT, DEPRECATED, "Deprecated", true),
}
