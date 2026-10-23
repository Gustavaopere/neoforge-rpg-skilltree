# Universal Material Chemistry — Validation Matrix

| Gate | Evidence required | Failure behavior |
|---|---|---|
| Canonical modlist coverage | every modlist row has audit disposition | fail coverage test |
| Candidate classification | every discovered candidate is supported or explicitly blocked | fail coverage test |
| Real chemistry | formula/composition + scientific provenance | block material; never guess |
| Provider equivalence | provider identity evidence for alias | keep separate identities |
| Destroy exact mapping | installed-version Destroy identity/API proof | no Destroy mapping |
| Fictional representation | curated arcane signature + lore/provenance class | block fictional representation |
| Hybrid representation | real and fictional portions stored separately | reject synthetic overall formula |
| Third-party reuse | exact version/license/notice obligations | independent rewrite or block reuse |
| Resource reload | atomic validated catalog replacement | retain last valid catalog |
| Client/server split | dedicated-server smoke | fail build/release gate |
| Provider drift | adapter enumeration vs catalog | fail CI with exact missing IDs |
| Destroy drift | enumerable Destroy IDs vs mappings | fail CI when enumeration is supported |
| UI hooks | installed API/hook verification | keep feature disabled/document blocker |
| Full-pack validation | existing project test matrix | do not merge |
| Post-merge validation | main SHA + required workflow success | implementation not declared complete |
