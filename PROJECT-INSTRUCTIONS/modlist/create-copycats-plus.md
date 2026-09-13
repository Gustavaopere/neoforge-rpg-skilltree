# Create: Copycats+

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8189ab08d53242600f01
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Copycats+
- **Arquivo JAR:** `copycats-3.0.9+mc.1.21.1-neoforge.jar`
- **Versão 1.21.1:** 3.0.9+mc.1.21.1-neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Visual
- **Função:** Expande os copycat blocks do Create com numerosas formas e variações decorativas que assumem materiais/blocos aplicados pelo jogador.
- **Dependências:** Create 6.0.10 físico. Integrações físicas relevantes: Aero Copycats 1.1.1 (metadata 1.1.0) e Extra Copycats 1.0.2; são addons separados, não conteúdo base duplicado.
- **Sobreposição:** Expande formas copycat do Create. Aero Copycats e Extra Copycats adicionam/integram shapes específicos e permanecem módulos separados; não tratar a coexistência como duplicação automática.
- **Compatibilidade/Riscos:** Riscos: material/state serialization, CT/model/light drift, schematics/contraptions perderem material, placement-assist inconsistente, x-ray/ghost-block regressions e overlap de shapes com addons. Updates devem validar Create 6 + Aero/Extra Copycats.
- **Observações:** JAR físico `copycats-3.0.9+mc.1.21.1-neoforge.jar`, mod id `copycats`, runtime 3.0.9+mc.1.21.1-neoforge. Release oficial NeoForge 1.21.1 de 06/09/2026, Client & Server. O texto antigo que ainda citava runtime 3.0.8 foi corrigido.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Create: Copycats+ 3.0.9 + changelog/source oficial da linha 3.0.x.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/copycats
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Copycats+ 3.0.9, shapes/material authority, CT/light, schematics/contraptions, placement, integrations físicas e regressões da linha 3.0.x confirmados no QC global #109. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 09/09/2026, Copycats+ 3.0.9 foi reconfirmado no JAR físico com Create 6.0.10 e addons físicos relacionados. A presença e o uso no stack Create não foram convertidos automaticamente em decisão curatorial de manter/remover.
- **Data da última decisão:**

> 🧱 **ESCOPO CANÔNICO.** Runtime físico: `copycats-3.0.9+mc.1.21.1-neoforge.jar`, mod id `copycats`, versão `3.0.9+mc.1.21.1-neoforge`, NeoForge 1.21.1. Copycats+ expande o sistema de copycat blocks do Create com novas formas que recebem material visual/funcional aplicado pelo jogador.

## 1. Identidade, versão e authority
- Provider base: Create 6.0.10.
- Copycats+ é owner das formas, estados e regras próprias dos seus copycat blocks.
- O material aplicado continua referenciando blocos/providers externos; Copycats+ não passa a ser owner do bloco-material original.
- A build física 3.0.9 é Release oficial de 06/09/2026 e Client & Server.

## 2. Superfície de conteúdo
O mod amplia o conceito nativo de copycat com numerosas geometrias e variações decorativas. O objetivo técnico é permitir que um mesmo material seja representado em formas que o Create base não oferece. O catálogo exato de registry IDs da 3.0.9 não foi inferido sem pin de source da build; a ficha trata shapes como catálogo provider-native e evita números fabricados.

## 3. Material, estado e Connected Textures
Cada copycat precisa manter duas classes de estado distintas: a geometria do bloco Copycats+ e o material aplicado. Model rendering, culling, tint e connected textures dependem da combinação. O material pode também fornecer emissão de luz, portanto alteração de material precisa atualizar iluminação e render sem deixar cache stale.
A linha 3.0.x contém correções específicas para CT no NeoForge, o que torna reload de resources e mudança de material regression gates concretos.

## 4. Placement assists e interação
Placement helpers fazem parte da experiência do mod. A linha 3.0.x alterou/corrigiu assists e ghost-block behavior; portanto colocar séries de shapes, substituir material e usar wrench precisam preservar orientation e não gerar bloco fantasma ou exploit de visão através de paredes.

## 5. Schematics e contraptions
Copycats+ integra com Create schematics e contraptions. O material aplicado e a geometria devem sobreviver a save/load, schematic placement, assembly/disassembly e movimento. A 3.0.4 corrigiu copycats funcionais durante contraption assembly, e versões anteriores da linha 3.x corrigiram erros de light update em schematics.
Nenhuma integração própria deve reconstruir o material apenas a partir do model do cliente; state persistido/server-side é a fonte correta.

## 6. Performance e shapes
Copycat blocks podem gerar collision/voxel shapes mais complexos do que blocos cúbicos. O upstream já otimizou shapes para assembly/disassembly em releases anteriores. Isso é evidência de que grandes contraptions decoradas com muitos copycats precisam entrar em teste de montagem e tick/render, especialmente junto ao stack físico Sable/Aeronautics.

## 7. Integrações físicas do pack
- Create 6.0.10: provider obrigatório.
- Aero Copycats 1.1.1: módulo separado para superfícies ligadas ao ecossistema Aeronautics.
- Extra Copycats 1.0.2: adiciona shapes extras; não faz parte do registry base desta ficha.
- Create Aeronautics/Sable: copycats podem existir em estruturas físicas móveis e exigem teste de persistência/render.

Esses addons devem consumir Copycats+ sem duplicar ownership do mesmo registry/state.

## 8. Client/server e multiplayer
Render, CT e preview são client-facing; block state, material aplicado, placement e alterações por wrench são server-authoritative. Em multiplayer, dois jogadores alterando o mesmo copycat não podem produzir material divergente entre clientes nem drop duplicado.

## 9. Riscos
1. Material aplicado desaparece após unload/restart.
2. CT/model cache fica stale após resource reload.
3. Light emission não atualiza ao trocar material.
4. Schematic copia shape mas perde material.
5. Contraption assembly/disassembly duplica ou reseta state.
6. Ghost-block/x-ray regressions reaparecem em placement assists.
7. Addon registra shape sobreposto ou assume API de versão anterior.
8. Large voxel shapes aumentam custo de assembly/collision/render.

## 10. Boundary para automação e projetos próprios
Copycat placement é construção/decoração. Não inferir progressão simplesmente porque um material aparece visualmente aplicado. Qualquer integração deve usar block state/interaction confirmada no servidor e respeitar ownership do Create/Copycats+.

## 11. Matriz de testes
- [ ] Dedicated server inicia com Copycats+ 3.0.9 + Create 6.0.10.
- [ ] Aplicar, trocar e remover material preserva stack/state exatamente uma vez.
- [ ] Connected textures e emissão de luz atualizam após troca de material e `/reload`.
- [ ] Placement assists não criam ghost block nem x-ray.
- [ ] Schematic salva e restaura shape + material.
- [ ] Contraption assemble/disassemble preserva shape + material sem dupe.
- [ ] Aero Copycats e Extra Copycats coexistem sem registry/model collision.
- [ ] Multiplayer converge para o mesmo material/state em todos os clientes.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
A release 3.0.9 e seu alvo NeoForge 1.21.1 estão confirmados oficialmente. O changelog público da linha 3.0.x sustenta as superfícies de CT, placement, schematics, contraptions, light updates e ghost-block regressions. Como o source crawl não expôs um changelog detalhado específico da 3.0.9, esta ficha não atribui a 3.0.9 mudanças não demonstradas; usa a build física como authority de versão e a linha 3.0.x como evidência arquitetural.