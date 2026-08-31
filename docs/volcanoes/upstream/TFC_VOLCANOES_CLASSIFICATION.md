# TFC Volcanoes 2.2.1 — Complete Source Classification

This inventory is generated from the Foundation inspection of `TFCVolcanoes-1.21.1-2.2.1.jar` (File ID `8710292`, SHA-256 `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`). The CI artifact contained 169 binary classes represented by 111 top-level decompiled Java source units. Nested and anonymous classes inherit the classification of their enclosing source unit.

`PORT` means substantially standalone behavior worth carrying forward after namespace/API/provenance review. `ADAPT` means valuable behavior that must be rewritten around Volcanoes-owned contracts or safer NeoForge hooks. `DROP` means the upstream class itself must not be carried into production.

Totals: **30 PORT**, **47 ADAPT**, **34 DROP**.

## PORT — 30

- `tfcvolcanoes/client/particles/AshParticle.java`
- `tfcvolcanoes/client/particles/GeyserOptions.java`
- `tfcvolcanoes/client/particles/GeyserParticle.java`
- `tfcvolcanoes/client/particles/PlumeOptions.java`
- `tfcvolcanoes/client/particles/PlumeParticleFactory.java`
- `tfcvolcanoes/client/particles/PyroclasticBombParticle.java`
- `tfcvolcanoes/client/particles/PyroclasticBombSeedParticle.java`
- `tfcvolcanoes/client/particles/PyroclasticFlowOptions.java`
- `tfcvolcanoes/client/particles/PyroclasticFlowParticle.java`
- `tfcvolcanoes/client/particles/PyroclasticFlowParticleFactory.java`
- `tfcvolcanoes/client/particles/SmokeParticle.java`
- `tfcvolcanoes/client/particles/SmokeTrailOptions.java`
- `tfcvolcanoes/client/particles/SmokeTrailParticle.java`
- `tfcvolcanoes/client/particles/SmokeTrailParticleFactory.java`
- `tfcvolcanoes/client/particles/VolcanoSmokeOptions.java`
- `tfcvolcanoes/client/particles/VolcanoSmokeParticleFactory.java`
- `tfcvolcanoes/client/render/entity/GeyserParticleRenderer.java`
- `tfcvolcanoes/client/render/entity/PyroclasticBombRenderer.java`
- `tfcvolcanoes/common/blocks/InverseBooleanProperty.java`
- `tfcvolcanoes/common/blocks/SingleIntProperty.java`
- `tfcvolcanoes/common/blocks/TFCVBlockStateProperties.java`
- `tfcvolcanoes/common/entities/GeyserParticle.java`
- `tfcvolcanoes/mixin/accessor/FireBlockAccessor.java`
- `tfcvolcanoes/network/ClientShakeData.java`
- `tfcvolcanoes/network/PacketHandler.java`
- `tfcvolcanoes/network/ShakePacket.java`
- `tfcvolcanoes/util/BlockPropertiesHelper.java`
- `tfcvolcanoes/util/ExposureMap.java`
- `tfcvolcanoes/util/TremorSavedData.java`
- `tfcvolcanoes/util/regions/VoronoiRegionMap.java`

## ADAPT — 47

- `tfcvolcanoes/TFCFForgeEventHandler.java`
- `tfcvolcanoes/TFCVolcanoes.java`
- `tfcvolcanoes/client/ClientEventHandler.java`
- `tfcvolcanoes/client/ClientHelpers.java`
- `tfcvolcanoes/client/TFCVColors.java`
- `tfcvolcanoes/client/TFCVSounds.java`
- `tfcvolcanoes/client/particles/PlumeParticle.java`
- `tfcvolcanoes/client/particles/TFCVParticles.java`
- `tfcvolcanoes/client/particles/VolcanoSmokeParticle.java`
- `tfcvolcanoes/client/render/blockentity/MineralSheetBlockEntityRenderer.java`
- `tfcvolcanoes/client/render/blockentity/MineralSheetBlockModel.java`
- `tfcvolcanoes/common/TFCVTags.java`
- `tfcvolcanoes/common/blockentities/MineralSheetBlockEntity.java`
- `tfcvolcanoes/common/blockentities/TFCVBlockEntities.java`
- `tfcvolcanoes/common/blockentities/TFCVPileBlockEntity.java`
- `tfcvolcanoes/common/blocks/CharredBlock.java`
- `tfcvolcanoes/common/blocks/PileBlock.java`
- `tfcvolcanoes/common/blocks/TFCVBlocks.java`
- `tfcvolcanoes/common/blocks/rock/Mineral.java`
- `tfcvolcanoes/common/blocks/rock/MineralSheetBlock.java`
- `tfcvolcanoes/common/commands/TFCVCommands.java`
- `tfcvolcanoes/common/entities/Geyser.java`
- `tfcvolcanoes/common/entities/PyroclasticBomb.java`
- `tfcvolcanoes/common/entities/PyroclasticFlow.java`
- `tfcvolcanoes/common/entities/PyroclasticFlowSegment.java`
- `tfcvolcanoes/common/entities/TFCVEntities.java`
- `tfcvolcanoes/common/items/TFCVItems.java`
- `tfcvolcanoes/config/Config.java`
- `tfcvolcanoes/mixin/blocks/BambooStalkBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/FireBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/MudBlockMixin.java`
- `tfcvolcanoes/mixin/client/BlockRendererMixin.java`
- `tfcvolcanoes/mixin/client/ModelBlockRendererMixin.java`
- `tfcvolcanoes/mixin/client/QuadLighterMixin.java`
- `tfcvolcanoes/mixin/client/accessor/AbstractBlockRenderContextAccessor.java`
- `tfcvolcanoes/mixin/fluids/FlowingFluidMixin.java`
- `tfcvolcanoes/mixin/fluids/HotWaterBlockMixin.java`
- `tfcvolcanoes/mixin/fluids/LavaFluidMixin.java`
- `tfcvolcanoes/mixin/fluids/LiquidBlockMixin.java`
- `tfcvolcanoes/mixin/items/BucketItemMixin.java`
- `tfcvolcanoes/util/FluidBlockEventHelpers.java`
- `tfcvolcanoes/util/TFCVHelpers.java`
- `tfcvolcanoes/util/TremorEvent.java`
- `tfcvolcanoes/util/noise/CinderFeatureNoise.java`
- `tfcvolcanoes/util/noise/DensityCellular2D.java`
- `tfcvolcanoes/util/regions/ItemRegionHelper.java`
- `tfcvolcanoes/util/regions/RockRegionHelper.java`

## DROP — 34

- `tfcvolcanoes/common/TFCVCreativeTabs.java`
- `tfcvolcanoes/common/recipes/TFCVCollapseRecipe.java`
- `tfcvolcanoes/common/recipes/TFCVRecipeSerializers.java`
- `tfcvolcanoes/common/recipes/TFCVRecipeTypes.java`
- `tfcvolcanoes/mixin/EntityGetterMixin.java`
- `tfcvolcanoes/mixin/FluidHelpersMixin.java`
- `tfcvolcanoes/mixin/ForgeEventHandlerMixin.java`
- `tfcvolcanoes/mixin/SoilBlockTypeMixin.java`
- `tfcvolcanoes/mixin/WorldTrackerMixin.java`
- `tfcvolcanoes/mixin/accessor/RegionChunkDataGeneratorAccessor.java`
- `tfcvolcanoes/mixin/accessor/WorldTrackerAccessor.java`
- `tfcvolcanoes/mixin/blocks/ConnectedGrassBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/CropBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/DecayingBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/DirtBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/FallenLeavesBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/FarmlandBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/LogBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/PlantBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/SeasonalPlantBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/TFCBushBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/TFCLeavesBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/TFCRootedDirtBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/TFCSaplingBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/TFCTorchBlockMixin.java`
- `tfcvolcanoes/mixin/blocks/TFCWallTorchBlockMixin.java`
- `tfcvolcanoes/mixin/entities/TFCFallingBlockEntityMixin.java`
- `tfcvolcanoes/mixin/items/TorchItemMixin.java`
- `tfcvolcanoes/util/Reflect.java`
- `tfcvolcanoes/util/TFCVInteractionManager.java`
- `tfcvolcanoes/util/TreeHelpers.java`
- `tfcvolcanoes/util/WorldTrackerExt.java`
- `tfcvolcanoes/util/regions/ItemRegionHelperExporter.java`
- `tfcvolcanoes/util/regions/RockRegionHelperExporter.java`

## Interpretation by subsystem

The central `ADAPT` targets are `TremorEvent` for eruption/seismic lifecycle, `FluidBlockEventHelpers` and `TFCVHelpers` for lava/geothermal/mineral/charring behavior, `PyroclasticBomb` plus the flow entities for eruptive hazards, and `RockRegionHelper`/`DensityCellular2D`/`ItemRegionHelper` for deterministic spatial assignment. `VoronoiRegionMap`, the network shake payloads, many client particles and a small set of state/cache utilities are the strongest `PORT` candidates.

The `DROP` set intentionally removes TFC collapse/landslide ownership, TFC agriculture/soil/tree lifecycle, TFC WorldTracker/region-generator access, TFC Registry API interaction tables, and diagnostic exporters. Generic wildfire or geological behavior may still be reimplemented through Volcanoes-owned tags/services, but not by carrying these TFC-specific classes forward.
