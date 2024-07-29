package net.rsprox.shared.property.regular

import net.rsprox.shared.property.ChildProperty

public class VarpProperty(
    override val propertyName: String,
    override val value: Int,
    override val children: MutableList<ChildProperty<*>> = mutableListOf(),
    override val type: Class<Int> = Int::class.java,
) : ChildProperty<Int>
