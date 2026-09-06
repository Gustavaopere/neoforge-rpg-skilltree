// Volcanoes Stage 06 coexistence selection for Create: Rock & Stone 1.3.1-1.21.1-6.
// Deploy this file into kubejs/startup_scripts before generating new chunks.
//
// Volcanoes physically materializes bounded hydrothermal copper, iron and gold ore bodies
// only at its own deterministic geothermal expressions. RNS remains authoritative for its
// native deposit worldgen, including copper, iron and gold outside those Volcanoes bodies.
//
// `true` keeps each RNS deposit selected/scannable AND keeps native RNS worldgen enabled.
// The Volcanoes lifecycle bridge adds only CustomServerDepositLocation prospecting metadata
// for already-authoritative Volcanoes hydrothermal Cu/Fe/Au deposits; it does not generate
// a second ore body and therefore does not require disabling an RNS metal family globally.
// Tin, nickel, zinc and silver remain entirely RNS-native and are not Volcanoes projections.
StartupEvents.rnsEnableDeposits(event => {
  event.overworld()
    .deposit('create_rns:deposit_coal', true)
    .deposit('create_rns:deposit_iron', true)
    .deposit('create_rns:deposit_copper', true)
    .deposit('create_rns:deposit_zinc', true)
    .deposit('create_rns:deposit_gold', true)
    .deposit('create_rns:deposit_lapis', true)
    .deposit('create_rns:deposit_redstone', true)
    .deposit('create_rns:deposit_tin', true)
    .deposit('create_rns:deposit_osmium', true)
    .deposit('create_rns:deposit_lead', true)
    .deposit('create_rns:deposit_nickel', true)
    .deposit('create_rns:deposit_silver', true)
    .deposit('create_rns:deposit_platinum', true)
    .deposit('create_rns:deposit_uranium', true)
    .deposit('create_rns:deposit_thorium', true)

  event.nether()
    .deposit('create_rns:deposit_nether_gold', true)
    .deposit('create_rns:deposit_nether_quartz', true)
    .deposit('create_rns:deposit_nether_cobalt', true)
    .deposit('create_rns:deposit_nether_wolframite', true)
})
