# AttributeFix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c2b9fae161e2f18d9d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AttributeFix
- **Arquivo JAR:** `attributefix-neoforge-1.21.1-21.1.3.jar`
- **Versão 1.21.1:** 21.1.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** QoL
- **Função:** Eleva/remove limites artificiais de atributos vanilla para permitir valores maiores usados por outros mods e sistemas RPG.
- **Dependências:** PrickleMC 21.1.11 físico. AttributeFix altera limites de atributos, não substitui os providers que calculam os valores efetivos.
- **Sobreposição:** Complementa mods que criam atributos; não equivale a Apothic Attributes, Additional Attributes ou outros providers.
- **Compatibilidade/Riscos:** Ao permitir valores mais altos, pode expor combinações extremas de atributos criadas por outros mods; não é um sistema de atributos novo.
- **Observações:** mod id: attributefix; runtime name: AttributeFix.
- **Procedência:** modlist.txt física atual de 08/09/2026 + source/documentação AttributeFix 21.1.3 + PrickleMC 21.1.11 físico + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/attributefix
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — AttributeFix 21.1.3, cinco vanilla attribute maxima elevados/configuráveis e provider-boundary confirmados no QC global #59. PrickleMC 21.1.11 reconfirmado; estado anterior `Integrado ao Github` preservado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou AttributeFix 21.1.3, os cinco ranges vanilla padrão, a configuração por atributo e a dependência PrickleMC. A instalação atual não foi convertida automaticamente em decisão de manter/remover.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `attributefix-neoforge-1.21.1-21.1.3.jar`, mod id `attributefix`, versão `21.1.3`. O source oficial da branch `1.21.1` foi auditado; a release 21.1.3 altera build dependencies, sem mudança funcional declarada.

## 1. Papel e autoridade
AttributeFix corrige ranges artificiais de `RangedAttribute` que ficam abaixo dos valores esperados por mods. **Não adiciona um sistema de atributos novo** e não aplica bônus por si só. Ele altera os limites mínimo/máximo que o registry de atributos aceita, permitindo que providers como mods RPG, combate, equipamentos e magia atinjam valores maiores sem clamp prematuro.

## 2. Arquitetura exata
No init, `AttributeFixMod` percorre **todos os entries de `BuiltInRegistries.ATTRIBUTE`**. Para cada atributo que seja `RangedAttribute`:
1. obtém o `ResourceLocation` do atributo;
2. constrói `RangeConfig` a partir dos limites atuais;
3. carrega uma configuração por atributo via PrickleMC;
4. aplica a configuração ao objeto `RangedAttribute`.

A alteração é feita por accessor mixin (`AccessorRangedAttribute`) sobre os campos de mínimo/máximo. Não há event loop de combate nem recalculador de modifiers.

## 3. Atributos vanilla alterados por padrão — 5
`RangeConfig.NEW_DEFAULT_VALUES` ativa modificação por padrão e eleva o **máximo para 1.000.000** em:
- `minecraft:generic.max_health`
- `minecraft:generic.armor`
- `minecraft:generic.armor_toughness`
- `minecraft:generic.attack_damage`
- `minecraft:generic.attack_knockback`

Para qualquer outro `RangedAttribute`, a config é criada usando os limites do provider e `modify_range` fica falso por padrão, salvo configuração explícita.

## 4. Configuração por atributo
Cada `RangeConfig` expõe:
- `modify_range`: habilita/desabilita alteração daquele atributo;
- `min`: mínimo desejado;
- `max`: máximo desejado.

A configuração é carregada em caminho derivado de `attributefix/<namespace>/<path>`, de modo que atributos de outros mods também podem receber ranges customizados sem hardcode no AttributeFix.

## 5. Fail-fast de configuração inválida
Se `min >= max`, o código:
- registra erro com id/min/max;
- lança `IllegalStateException`.

Isso é comportamento fail-closed correto. Não contornar esse gate em mod próprio e não substituir automaticamente valores inválidos.

## 6. O que AttributeFix NÃO muda
- valor base efetivo de uma entidade, salvo o range aceitar o valor;
- modifiers concedidos por itens/perks/effects;
- fórmula de damage/armor do Minecraft ou de outro combat provider;
- sync de atributo;
- persistência de modifiers;
- ordem de operações dos modifiers;
- caps internos que outro mod imponha depois.

Portanto “AttributeFix instalado” não significa que todo atributo possa crescer infinitamente em qualquer sistema; significa apenas que o `RangedAttribute` pode ter seu range configurado mais alto.

## 7. Dependência PrickleMC
O source usa `net.darkhax.pricklemc` para config management. A modlist física atual confirma **PrickleMC 21.1.11** instalado. Isso é dependência real da infraestrutura de configuração; não remover Prickle sem verificar metadata/dependents.

## 8. Relação com Apothic Attributes e sistemas RPG
Apothic Attributes, RPG perks e outros mods podem registrar/modificar atributos. AttributeFix não compete como authority: ele ajusta o **range permitido**, enquanto o provider do atributo continua definindo valor base/modifiers e significado.

Risco típico: após remover um clamp, combinações extremas antes invisíveis tornam-se alcançáveis. Isso pode revelar overflow, fórmula não balanceada ou UI incapaz de mostrar valores grandes em outro mod. O problema deve ser corrigido no provider/fórmula correspondente, não restaurando silenciosamente um clamp arbitrário.

## 9. Ordem de inicialização e registry
Como o init itera `BuiltInRegistries.ATTRIBUTE`, a aplicação depende de os atributos relevantes já estarem registrados no ponto em que o mod executa. Para atributos modded, validar que config foi gerada/aplicada e que não existe outro mod reescrevendo o mesmo min/max posteriormente.

Se dois mods mutam o range do mesmo `RangedAttribute`, a ordem passa a ser semanticamente relevante. Registrar isso como conflito real; não assumir que o maior range “vence” de forma estável.

## 10. Client/server e multiplayer
Atributos são gameplay state, portanto o servidor é autoridade dos valores efetivos. AttributeFix precisa estar coerente com o ambiente do pack onde os atributos são calculados/sincronizados.

Testar:
- dedicated server;
- login/relogin;
- respawn;
- dimension change;
- modifiers adicionados/removidos acima do antigo cap;
- cliente recebendo valor sincronizado sem truncamento visual/protocolar.

## 11. Riscos numéricos
Com máximo de 1.000.000 nos cinco atributos padrão, testar especialmente:
- max health alto + health current/save/reload;
- armor/armor toughness muito altos em fórmulas de damage reduction de mods;
- attack damage alto contra health baixo/alto;
- knockback extremo gerando velocity/position aberrante;
- serialização, UI e tooltips;
- conversões `float` em consumidores apesar do atributo usar `double`.

AttributeFix em si não promete que todos consumidores suportem matematicamente 1.000.000.

## 12. Testes/regressões obrigatórios
1. Confirmar os cinco vanilla ranges em runtime após startup.
2. Atributo não listado permanece com range original quando `modify_range=false`.
3. Config custom de atributo modded aplica apenas ao id alvo.
4. `min >= max` falha como previsto.
5. Reinício preserva configs e ranges.
6. Equip/perk que passa do cap vanilla mantém valor e remove corretamente depois.
7. Dedicated server e multiplayer sync com valores altos.
8. Epic Fight/Apothic/RPG: damage/armor continuam sendo calculados pelo provider correto.
9. Sem overflow/NaN/Infinity em casos extremos do pack.

## 13. Evidência
- Modlist física atual: AttributeFix 21.1.3 e PrickleMC 21.1.11.
- CurseForge/Modrinth oficial: release 21.1.3 para NeoForge 1.21.1; changelog “Update build dependencies”.
- Source oficial `Darkhax-Minecraft/AttributeFix`, branch `1.21.1`.
- `AttributeFixMod`, `RangeConfig` e `AccessorRangedAttribute` auditados.

> 📐 Exaustividade apropriada ao escopo: a implementação inteira relevante cabe essencialmente no scan de `RangedAttribute`, config por id e accessor de min/max. Os cinco defaults de 1.000.000 estão enumerados; nenhum comportamento de gameplay adicional foi atribuído ao mod.
