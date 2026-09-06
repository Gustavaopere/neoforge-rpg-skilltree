# Architectural Decisions

These decisions are binding until deliberately changed in a reviewed commit.

1. **Worldgen ownership:** Terralith + Tectonic + BWG remain the Overworld stack. Volcanoes overlays geology/tectonics/volcanism; it does not introduce `tfc:overworld`.
2. **TFC removal:** no runtime dependency on TerraFirmaCraft or TFC Registry API. TFC rock/world queries are replaced with internal registries, tags and deterministic services.
3. **Earthquakes:** tectonic stress matters for plate behavior and volcanism. Default seismic gameplay is non-destructive. Terrain damage, if ever enabled, must be opt-in, small-scale, natural-block-only and claim/structure aware.
4. **MineCollapse:** not a dependency. It remains excluded unless a future verified integration can guarantee no ordinary MineColonies/mining disruption.
5. **MineColonies safety:** colony/player structures are protected from default earthquake/eruption block damage. Catastrophic destruction is not a design goal.
6. **Atmosphere:** one vector state, not several unrelated air systems. It distinguishes pressure, O2, CO2, SO2/acid gases, toxic gases, particulates/smoke, humidity and thermal modifier.
7. **Respiration:** use NeoForge `LivingBreatheEvent`; adapt the proven ThinAir approach but replace its four-level core with the vector atmosphere model.
8. **Pressure:** atmospheric and hydrostatic pressure use one pressure service. Water pressure is continuous with depth, not hard-coded Y zones.
9. **Destroy:** Destroy remains the chemistry/pollution authority when installed. Volcanoes emits volcanic pollutants and reads industrial pollution through an optional adapter. No second competing pollution ecosystem.
10. **Cold Sweat:** remains the temperature authority when installed. Volcanoes supplies environmental heat/cold modifiers instead of implementing a parallel body-temperature system.
11. **Create/Aeronautics/Sable:** existing diving/backtank/vehicle systems are recognized through optional adapters. Enclosed vehicles should eventually use internal rather than outside atmosphere/pressure.
12. **RNS:** Create Rock & Stone remains the player-facing prospecting system and native deposit-worldgen authority when installed. Volcanoes exposes geology/deposit classification and only projects bounded, physically proven hydrothermal Cu/Fe/Au bodies as RNS custom/scannable locations; native RNS worldgen remains enabled for Cu/Fe/Au/Sn/Ni/Zn/Ag. Volcanoes must not infer ownership from value equality: only custom RNS records carrying the matching durable Volcanoes source UUID marker may be rebound or removed after restart.
13. **Data-driven compatibility:** blocks, ores and protection equipment are classified through tags/datapacks/config where possible.
14. **Performance:** no 5x5-chunk search on every breathing tick. Atmosphere and geology use cached fields/indexed local sources with bounded updates.
15. **Hydrothermal mineral identity:** an exact Stage 01 metal identity is assigned only when deterministic geothermal worldgen can prove a volcanic magma contribution above the tectonic baseline. Shield and fissure systems classify as `c:ores/iron`, stratovolcano systems as `c:ores/copper`, and caldera systems as `c:ores/gold`; purely tectonic hydrothermal systems remain `volcanoes:resources/mineral`. Identity metadata alone is not proof of physical ore realization. A Volcanoes RNS projection additionally requires the bounded physical producer to have realized the matching Cu/Fe/Au body. Physical realization authorizes only the corresponding Volcanoes custom prospecting projection; it does **not** transfer or disable RNS native worldgen ownership.
