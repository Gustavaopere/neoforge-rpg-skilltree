#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
owner = ROOT / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/A0081A0100RuntimeState.java'
events = ROOT / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/events/A0081A0100CombatEvents.java'
bootstrap = ROOT / 'src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java'
attributes = ROOT / 'src/main/java/dev/gustavopere/rpgskilltree/runtime/effects/AttributeNodeEffectRuntime.java'

for path in (owner, events):
    if not path.exists():
        raise SystemExit(f'A0081-A0100 runtime validation: FAIL missing {path.relative_to(ROOT)}')

owner_text = owner.read_text()
events_text = events.read_text()
bootstrap_text = bootstrap.read_text()
attributes_text = attributes.read_text()

required_owner = ['SustainResolver', 'CombatRecoveryService', 'A0081A0100DefenseState', 'CombatPerkNodeBinding']
for token in required_owner:
    if token not in owner_text:
        raise SystemExit(f'A0081-A0100 runtime validation: FAIL runtime owner missing {token}')

required_events = [
    'LivingIncomingDamageEvent', 'LivingDamageEvent.Post', 'ServerTickEvent.Post',
    'A0061A0080RuntimeState', 'stationary()', 'SustainResolver', 'CombatRecoveryService',
    'A0081A0100DefenseState', 'FAIL_CLOSED_A0093', 'FAIL_CLOSED_A0094', 'FAIL_CLOSED_A0095',
    'FAIL_CLOSED_A0100'
]
for token in required_events:
    if token not in events_text:
        raise SystemExit(f'A0081-A0100 runtime validation: FAIL combat events missing {token}')

if 'A0081A0100CombatEvents.class' not in bootstrap_text:
    raise SystemExit('A0081-A0100 runtime validation: FAIL event owner is not registered in bootstrap')

if 'A0081A0100CombatPolicy.preserveHealthRatio' not in attributes_text:
    raise SystemExit('A0081-A0100 runtime validation: FAIL A0088 health-ratio preservation is not wired into attribute refresh')

# The current provider surface does not expose safe causal guard debit/recovery/interruption
# receipts nor generic third-party critical decomposition. These effects must stay explicitly
# fail-closed rather than being approximated from damage, knockback, animation or post-refunds.
for forbidden in ('estimateGuard', 'refundGuard', 'guessCritical', 'presumedCriticalMultiplier'):
    if forbidden in events_text:
        raise SystemExit(f'A0081-A0100 runtime validation: FAIL forbidden heuristic {forbidden}')

print('A0081-A0100 runtime integration validation: PASS')
