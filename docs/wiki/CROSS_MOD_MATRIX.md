# Matriz cross-mod

| Mod | Estado | O que está comprovado | O que não deve ser presumido |
| --- | --- | --- | --- |
| Epic Fight | IMPLEMENTED | bridge/runtime de combate e node effects | dupla aplicação com fallback vanilla |
| Iron's Spellbooks | IMPLEMENTED | gating Arcano, mastery por casts, inscrição permanente condicionada por mastery + Mage | bônus nominal para todo spell; Echo Cast/Overchannel sem prova de runtime |
| Ars Nouveau | IMPLEMENTED | adapter runtime dedicado | efeito nominal por glyph/spell sem handler comprovado |
| Goety | IMPLEMENTED | Soul Energy, identidades Warlock/Necromancer, mastery por ações confirmadas | que todo evento do mod concede mastery |
| Malum | IMPLEMENTED | mastery por spirit harvesting/reaping confirmado | mastery por simples interação sem colheita |
| Eidolon: Repraised | IMPLEMENTED | mastery/discovery por receita de Crucible concluída | progresso por tentativa incompleta |
| Identity2 | PARTIAL/VERIFY | contrato de identidade/progressão presente no desenho do projeto | efeitos específicos não revalidados nesta edição |
| Create | SPEC/DATA | especialização/progressão definida em dados/specs | adapter runtime, mastery por máquinas ou bônus de engenharia automáticos |
| Applied Energistics 2 | SPEC/DATA | especialização/progressão definida em dados/specs | adapter runtime ou mastery por crafting/network sem prova |
| Apothic/boss bridges | UNCONFIRMED | documentação/histórico pode mencionar integração de bosses | suporte nominal até a classe/bridge atual ser revalidada |

## Regra de interpretação

`IMPLEMENTED` indica evidência no runtime/dados atuais, não garantia de que toda feature possível do mod externo esteja coberta. Para qualquer bônus numérico, o JSON/node effect/adapter correspondente é a fonte final.