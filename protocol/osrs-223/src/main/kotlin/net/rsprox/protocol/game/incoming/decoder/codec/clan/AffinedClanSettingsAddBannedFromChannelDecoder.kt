package net.rsprox.protocol.game.incoming.decoder.codec.clan

import net.rsprot.buffer.JagByteBuf
import net.rsprot.protocol.ClientProt
import net.rsprot.protocol.metadata.Consistent
import net.rsprox.protocol.ProxyMessageDecoder
import net.rsprox.protocol.game.incoming.decoder.prot.GameClientProt
import net.rsprox.protocol.game.incoming.model.clan.AffinedClanSettingsAddBannedFromChannel
import net.rsprox.protocol.session.Session

@Consistent
public class AffinedClanSettingsAddBannedFromChannelDecoder :
    ProxyMessageDecoder<AffinedClanSettingsAddBannedFromChannel> {
    override val prot: ClientProt = GameClientProt.AFFINEDCLANSETTINGS_ADDBANNED_FROMCHANNEL

    override fun decode(
        buffer: JagByteBuf,
        session: Session,
    ): AffinedClanSettingsAddBannedFromChannel {
        val clanId = buffer.g1()
        val memberIndex = buffer.g2()
        val name = buffer.gjstr()
        return AffinedClanSettingsAddBannedFromChannel(
            name,
            clanId,
            memberIndex,
        )
    }
}
