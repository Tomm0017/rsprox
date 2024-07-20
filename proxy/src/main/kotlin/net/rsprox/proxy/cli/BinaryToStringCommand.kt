package net.rsprox.proxy.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import net.rsprox.proxy.binary.BinaryBlob
import net.rsprox.proxy.binary.StreamDirection
import net.rsprox.proxy.config.BINARY_PATH
import net.rsprox.proxy.huffman.HuffmanProvider
import net.rsprox.proxy.plugin.DecodingSession
import net.rsprox.proxy.plugin.PluginLoader
import java.nio.file.Path
import kotlin.io.path.bufferedWriter
import kotlin.io.path.exists
import kotlin.io.path.nameWithoutExtension

public class BinaryToStringCommand : CliktCommand(name = "tostring") {
    private val name by option("-name")

    override fun run() {
        val pluginLoader = PluginLoader()
        HuffmanProvider.load()
        pluginLoader.loadPlugins("osrs")
        val fileName = this.name
        if (fileName != null) {
            val binaryName = if (fileName.endsWith(".bin")) fileName else "$fileName.bin"
            val file = BINARY_PATH.resolve(binaryName)
            if (!file.exists()) {
                echo("Unable to locate file $fileName in $BINARY_PATH")
                return
            }
            simpleTranscribe(file, pluginLoader)
        } else {
            val fileTreeWalk =
                BINARY_PATH
                    .toFile()
                    .walkTopDown()
                    .filter { it.extension == "bin" }
            for (bin in fileTreeWalk) {
                if (BINARY_PATH.resolve(bin.nameWithoutExtension + ".txt").exists()) {
                    continue
                }
                simpleTranscribe(bin.toPath(), pluginLoader)
            }
        }
    }

    private fun simpleTranscribe(
        binaryPath: Path,
        pluginLoader: PluginLoader,
    ) {
        val binary = BinaryBlob.decode(binaryPath)
        val latestPlugin = pluginLoader.getPlugin(binary.header.revision)
        val session = DecodingSession(binary, latestPlugin)
        var tick = 0
        val output =
            binaryPath.parent
                .resolve("${binaryPath.nameWithoutExtension}.txt")
                .bufferedWriter()
        output.write("------------------")
        output.newLine()
        output.write("Header information")
        output.newLine()
        output.write("version: ${binary.header.revision}.${binary.header.subRevision}")
        output.newLine()
        output.write("client type: ${binary.header.clientType}")
        output.newLine()
        output.write("platform type: ${binary.header.platformType}")
        output.newLine()
        output.write(
            "world: ${binary.header.worldId}, host: ${binary.header.worldHost}, " +
                "flags: ${binary.header.worldFlags}, location: ${binary.header.worldLocation}, " +
                "activity: ${binary.header.worldActivity}",
        )
        output.newLine()
        output.write("local player index: ${binary.header.localPlayerIndex}")
        output.newLine()
        output.write("-------------------")
        output.newLine()
        for ((direction, packet) in session.sequence()) {
            val string = StringBuilder()
            string.append('[').append(tick).append(']')
            string.append(if (direction == StreamDirection.CLIENT_TO_SERVER) " -> " else " <- ")
            string.append(packet)
            output.write(string.toString())
            output.newLine()
            if (packet.toString() == "ServerTickEnd") {
                tick++
            }
        }
        output.flush()
        output.close()
        echo("Binary file decoded into ${binaryPath.nameWithoutExtension}.txt in $BINARY_PATH")
    }
}

public fun main(args: Array<String>) {
    BinaryToStringCommand().main(args)
}