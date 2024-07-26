package net.rsprox.transcriber.base

import net.rsprot.protocol.Prot
import net.rsprox.cache.api.Cache
import net.rsprox.cache.api.CacheProvider
import net.rsprox.shared.SessionMonitor
import net.rsprox.transcriber.ClientPacketTranscriber
import net.rsprox.transcriber.MessageConsumerContainer
import net.rsprox.transcriber.ServerPacketTranscriber
import net.rsprox.transcriber.Transcriber
import net.rsprox.transcriber.state.StateTracker

public class BaseTranscriber private constructor(
    private val container: MessageConsumerContainer,
    formatter: BaseMessageFormatter,
    private val stateTracker: StateTracker,
    cacheProvider: CacheProvider,
    override val monitor: SessionMonitor<*>,
) : Transcriber,
    ClientPacketTranscriber by BaseClientPacketTranscriber(
        formatter,
        container,
        stateTracker,
        cacheProvider.get(),
        monitor,
    ),
    ServerPacketTranscriber by BaseServerPacketTranscriber(
        formatter,
        container,
        stateTracker,
        cacheProvider.get(),
        monitor,
    ) {
    public constructor(
        container: MessageConsumerContainer,
        cacheProvider: CacheProvider,
        monitor: SessionMonitor<*>,
        stateTracker: StateTracker,
    ) : this(
        container,
        BaseMessageFormatter(),
        stateTracker,
        cacheProvider,
        monitor,
    )

    override val cache: Cache = cacheProvider.get()

    override fun setCurrentProt(prot: Prot) {
        stateTracker.currentProt = prot
    }
}
