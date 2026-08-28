# Matriz cross-mod

| Mod/ecossistema | Estado | O que está comprovado | O que não deve ser presumido |
| --- | --- | --- | --- |
| Epic Fight | IMPLEMENTED | bridge/runtime e node effects de stamina, stamina regen e impact | dupla aplicação com fallback vanilla |
| Iron's Spellbooks | IMPLEMENTED | gating Arcano, mastery por casts e diversos atributos usados por node effects | bônus nominal para todo spell; `Echo Cast`/`Overchannel` sem prova de runtime |
| Ars Nouveau | IMPLEMENTED | adapter runtime dedicado | efeito nominal por glyph/spell sem handler comprovado |
| Goety | IMPLEMENTED | eventos confirmados alimentando mastery e regras Occult/Soul Energy no runtime | que todo evento do mod concede mastery |
| Malum | IMPLEMENTED | mastery por spirit harvesting/reaping e quatro atributos Malum em node effects | mastery por simples interação sem colheita |
| Eidolon: Repraised | IMPLEMENTED | mastery/discovery por receita de Crucible concluída | progresso por tentativa incompleta |
| Apothic Attributes | ATTRIBUTE REFERENCES PRESENT | vários node effects usam atributos Apothic | bridge dedicada de bosses/Apothic não comprovada por esta evidência |
| Identity2 | PARTIAL/VERIFY | contrato de identidade/progressão presente no desenho do projeto | efeitos específicos não revalidados nesta edição |
| Create | SPEC/DATA | especialização/progressão e nomes de node effects Technomancer relacionados a Create | adapter de máquinas/eventos; os efeitos `create_*` auditados alteram atributos Iron's |
| Applied Energistics 2 | SPEC/DATA | especialização/progressão definida em dados/specs | adapter runtime ou mastery por crafting/network sem prova |
| Apothic boss bridges | UNCONFIRMED | histórico pode mencionar integração de bosses | suporte nominal até a bridge atual ser revalidada |

## Regra de interpretação

`IMPLEMENTED` indica evidência no runtime/dados atuais, não garantia de cobertura integral do mod externo. Para qualquer bônus numérico, `node_effects/*.json` é a fonte final; para comportamento/eventos, o adapter Java correspondente é a fonte final.