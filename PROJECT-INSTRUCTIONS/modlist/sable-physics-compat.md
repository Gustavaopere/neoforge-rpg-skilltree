# Sable: Physics Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8176b2beca4310569d8b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sablephysicscompat-1.3.0.jar`, mod id `sablephysicscompat`, runtime `1.3.0`; Sable 2.0.5, Supplementaries 3.9.8, Quark 4.1-483, Ice and Fire CE 2.1.2, L_Ender's Cataclysm 3.33, Sable Beyond 0.5.0 e SableMassView 1.0.0 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Physics Compat 1.3.0 e os providers/runtime integrations citados acima estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable: Physics Compat
- **Arquivo JAR:** `sablephysicscompat-1.3.0.jar`
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia
- **Função:** Compatibilidade data-driven que fornece tags/propriedades físicas Sable para blocos modded, incluindo mass/weight, floating, friction, bounciness e mappings auxiliares.
- **Dependências:** Sable 2.0.5 é provider físico. Cobertura runtime confirmada no pack inclui Supplementaries 3.9.8, Quark 4.1-483, Ice and Fire CE 2.1.2 e L_Ender's Cataclysm 3.33; outros suportes upstream só ficam ativos se o provider existir.
- **Sobreposição:** Complementa Sable Beyond e MassView, mas não os duplica: fornece dados de bloco; Beyond adiciona runtime mechanics; MassView só exibe massa.
- **Compatibilidade/Riscos:** Compat data-driven ampla. Riscos: tags demasiado amplas/ausentes, IDs de providers mudando, stale properties após reload, datapacks conflitantes, valores físicos extremos e interação agregada com Sable Beyond.
- **Observações:** A 1.3.0 adiciona Architect's Palette, Macaw's e Storage Drawers, tag piston para Supplementaries Spring Launcher e amplia airtight/floating. Macaw/Storage Drawers não foram encontrados top-level no snapshot atual.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sable: Physics Compat 1.3.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-physics-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sable: Physics Compat 1.3.0 reconstruído: tags/properties data-driven, coverage ativa, 1.3.0 deltas, reload, ownership, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sablephysicscompat-1.3.0.jar`, mod id `sablephysicscompat`, versão `1.3.0`, NeoForge 1.21.1. É um pacote de **compatibilidade data-driven** para Sable/Aeronautics: fornece tags/propriedades físicas coerentes para blocos de dezenas de mods, em vez de criar outro solver de física.

## 1. Identidade e papel
- **Mod:** Sable: Physics Compat.
- **JAR:** `sablephysicscompat-1.3.0.jar`.
- **Mod id:** `sablephysicscompat`.
- **Versão:** `1.3.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Papel:** fornecer tags/propriedades Sable para blocos modded.

## 2. Natureza data-driven
A descrição oficial enfatiza propriedades como:
- floating;
- friction;
- bounciness;
- weight/mass;
- airtight e tags auxiliares em builds recentes.

O mod não substitui o Sable: ele alimenta o sistema de propriedades que o Sable usa para calcular comportamento físico de block states.

## 3. Authority e ownership
- **Sable:** schema/semântica das propriedades físicas, physics body e solver.
- **Physics Compat:** mapeamentos/tags/valores para blocos de terceiros.
- **Mod do bloco:** identidade, block state e gameplay original.

Um bloco coberto continua pertencendo ao provider original; o compat só descreve como ele deve participar da física Sable.

## 4. Cobertura ampla
O projeto declara cobertura de aproximadamente uma centena de mods e lista 81+ explicitamente em sua documentação. A presença de uma integração upstream não significa que o provider esteja instalado neste pack.

No snapshot físico atual, há integrações diretamente relevantes para **Supplementaries 3.9.8**, **Quark 4.1-483**, **Ice and Fire Community Edition 2.1.2** e **L_Ender's Cataclysm 3.33**.

## 5. Delta exato da 1.3.0 — novos providers
A release 1.3.0 adiciona suporte para:
- Architect's Palette;
- Macaw's Bridges/Fences/Furniture/Lights/Paths/Stairs/Windows;
- Storage Drawers.

Esses nomes são delta upstream. Macaw/Storage Drawers não foram encontrados como JAR top-level no snapshot atual, portanto não são marcados como integrações runtime ativas nesta ficha.

## 6. Delta 1.3.0 — Supplementaries Spring Launcher
A 1.3.0 adiciona tag `piston` para o **Spring Launcher** do Supplementaries. Como Supplementaries está fisicamente presente, esse mapeamento é regression gate concreto.

Testar interação com assembly/movimento e assegurar que a propriedade/tag não transforme outro bloco não relacionado em piston-like por tag excessivamente ampla.

## 7. Airtight support
A build expande suporte airtight para blocos leves/decorativos. Airtightness pode afetar integrações de pressão/ambiente que consultem propriedades físicas do ecossistema.

A ficha não atribui comportamento específico de oxigênio/pressurização a Physics Compat sem provider correspondente; documenta apenas o mapeamento publicado.

## 8. Floating support
A 1.3.0 expande floating para blocos End-themed e integrações adicionais de Aether, Quark, Ice and Fire, Cataclysm, Paradise Lost e Immersive Engineering.

No pack, Quark, Ice and Fire e Cataclysm estão confirmados. O resultado físico final continua dependente de massa, volume, fluidos e demais dados do Sable; tag floating não é garantia de estabilidade de uma estrutura completa.

## 9. Friction e bounciness
Versões anteriores da mesma linha já introduziram propriedades como super-bouncy/perfect-bouncy/super-sticky. Essas propriedades formam lineage de regressão, não delta exclusivo da 1.3.0.

Blocos com comportamento extremo precisam ser testados em colisão real; tooltip/tag correta não prova solver correto em grandes velocities.

## 10. Relação com Sable Beyond
Physics Compat e Sable Beyond atuam em camadas diferentes:
- Physics Compat fornece **dados/propriedades de blocos**;
- Beyond adiciona **mecânicas e integrações de runtime** como massa dinâmica, fluid forces, fans e basins.

Pode haver interação nos resultados, mas não são substitutos diretos. Remover um porque o outro existe apagaria uma camada diferente do sistema.

## 11. Relação com MassView
SableMassView 1.0.0 apenas mostra a massa efetiva nos advanced tooltips. É uma ferramenta útil para inspecionar se os mapeamentos do Physics Compat parecem aplicados, mas não valida automaticamente floating/friction/bounce/airtight.

## 12. Reload e datapacks
Como o domínio é data-driven, validar reload/restart quando properties/tags forem alteradas. O mesmo block state não deve continuar usando property antiga depois de um data reload válido.

Mundos existentes com structures montadas também precisam de teste: mudança de property pode alterar massa/behavior de body ao remontar/recalcular.

## 13. Client / Server
As propriedades que afetam física funcional devem convergir no servidor. Cliente pode precisar dos mesmos dados para tooltip/render/prediction, mas não deve decidir massa/float de forma independente.

Mismatch de data packs entre cliente/servidor não deve produzir duas físicas autoritativas.

## 14. Riscos técnicos
1. **Tag demasiado ampla:** bloco recebe property inadequada.
2. **Tag missing:** bloco modded cai em default pouco realista.
3. **Provider update:** IDs/tags de mod mudam e mapping fica stale.
4. **Data reload stale:** body conserva property antiga.
5. **Conflicting datapacks:** dois packs atribuem overrides incompatíveis.
6. **Extreme values:** bounciness/friction/mass expõem instabilidade de solver.
7. **Airtight semantic drift:** outro provider interpreta tag de modo diferente.
8. **Overlap com Beyond:** resultado agregado parece duplo embora camadas sejam distintas.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Sable 2.0.5 + Physics Compat 1.3.0.
- [ ] Supplementaries Spring Launcher recebe comportamento/tag esperado.
- [ ] Quark blocks cobertos apresentam propriedades coerentes.
- [ ] Ice and Fire blocks cobertos apresentam propriedades coerentes.
- [ ] Cataclysm blocks cobertos apresentam propriedades coerentes.
- [ ] Floating block realmente flutua no cenário de referência sem physics instability.
- [ ] Friction/bouncy mapping produz resposta coerente em colisão.
- [ ] MassView reflete massa atual quando o mapping de mass é aplicável.
- [ ] Reload/restart após mudança de datapack não deixa property stale.
- [ ] Sable Beyond coexistente não duplica massa/propriedade do mesmo bloco.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física atual: Physics Compat 1.3.0, Sable 2.0.5, Supplementaries 3.9.8, Quark 4.1-483, Ice and Fire CE 2.1.2 e Cataclysm 3.33.
- CurseForge oficial 1.3.0: lista de novos providers e expansão de piston/airtight/floating support.
- CurseForge oficial do projeto: natureza de compat e propriedades floating/friction/bounciness/weight.
- **Limite:** não foi inventariada cada tag JSON do JAR nem afirmado suporte ativo para mods ausentes da modlist.
