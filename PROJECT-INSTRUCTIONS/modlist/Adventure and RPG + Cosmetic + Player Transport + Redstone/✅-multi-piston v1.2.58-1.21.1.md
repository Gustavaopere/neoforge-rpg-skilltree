# Multi-Piston

## Propriedades do registro

- **Mod:** Multi-Piston
- **Arquivo JAR:** multipiston-1.2.58-1.21.1.jar
- **Versão 1.21.1:** 1.2.58-1.21.1
- **Categoria:** Tecnologia, Biblioteca
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/multi-piston
- **Função:** Implementa pistão/movimentação multidirecional e infraestrutura para deslocamento de blocos/block entities usada pelo ecossistema LDTTeam/MineColonies.
- **Dependências:** NeoForge 1.21.1. Consumer confirmado: MineColonies 1.1.1387 exige Multi-Piston \>=1.2.51; runtime físico 1.2.58-1.21.1 satisfaz o mínimo.
- **Compatibilidade/Riscos:** Dependency do MineColonies 1.1.1387. Riscos: block-entity data/capability loss, chunk-boundary movement, redstone/update loops e interação com block entities de outros mods. 1.2.58 altera movimentação de mais block entities.
- **Sobreposição:** Não é equivalente a contraptions/pistons do Create; lifecycle e contratos são distintos. É dependência operacional de MineColonies.
- **Observações:** Runtime literal 1.2.58-1.21.1; file ID 7097877, Release de 12/10/2025. Changelog exato: `move some more blockentities`. Sources/javadocs oficiais são publicados para a release.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial Multi-Piston 1.2.58 + dependency MineColonies 1.1.1387 já auditada.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — Multi-Piston 1.2.58-1.21.1/JAR físico reconfirmado. Consumer MineColonies físico atualizado para 1.1.1387; mínimo Multi-Piston \>=1.2.51 continua satisfeito. Decisão Dependência preservada.
- **Histórico da decisão:** 2026-08-26 — versão normalizada para o runtime literal 1.2.58-1.21.1 e classificada como Dependência por exigência confirmada de MineColonies.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #413: JAR `multipiston-1.2.58-1.21.1.jar`, mod id `multipiston`, runtime `1.2.58-1.21.1`, SHA-1 `2b63be739da24247328e4052ec91c928230d0390`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `multipiston-1.2.58-1.21.1.jar`, mod id `multipiston`, versão literal `1.2.58-1.21.1`. A release oficial exata é o file ID `7097877`, publicada em 12/10/2025 para NeoForge 1.21/1.21.1. MineColonies 1.1.1387 instalado exige Multi-Piston 1.2.51 ou superior; portanto este JAR é **dependência operacional confirmada** do stack MineColonies.
</callout>
## 1. Identidade e papel
- **Mod:** Multi-Piston.
- **JAR físico:** `multipiston-1.2.58-1.21.1.jar`.
- **Mod id:** `multipiston`.
- **Runtime:** `1.2.58-1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** LDTTeam / Raycoms.
- **Licença:** GPLv3.
- **Papel:** implementar pistão/movimentação multidirecional e fornecer infraestrutura de movimentação de blocos usada pelo ecossistema LDTTeam/MineColonies.
- **Decisão:** Dependência.
## 2. Conteúdo e função
A descrição oficial resume o projeto como um **Multi-directional Piston Block**. O valor funcional não é apenas cosmético: o mod fornece uma implementação própria de movimento de blocos e block entities, utilizada como componente técnico pelo ecossistema MineColonies.
Não deve ser confundido com contraptions do Create. Create possui sua própria autoridade e lifecycle para estruturas móveis; Multi-Piston resolve uma superfície distinta.
## 3. Dependência de MineColonies
A release física de MineColonies `1.1.1387-1.21.1-snapshot` exige **Multi-Piston \>=1.2.51**. O pack contém `1.2.58-1.21.1`, portanto o mínimo é satisfeito.
Consequência operacional:
- remover Multi-Piston isoladamente pode impedir o carregamento do MineColonies;
- atualização de Multi-Piston precisa ser regressada com MineColonies/Structurize e construções em andamento;
- o fato de o próprio projeto Multi-Piston não listar dependencies externas não reduz seu papel como dependency **de outro mod**.
## 4. Release 1.2.58
O changelog exato da 1.2.58 registra **“move some more blockentities”**. Isso é evidência direta de que movimentação de block entities é superfície ativa desta versão.
Não se deve extrapolar a lista exata de classes/blocos suportados a partir dessa frase. A regra segura é testar block entities reais do pack que possam ser movidas pelo sistema.
## 5. Block entities e persistência
Mover um bloco com estado é mais sensível que mover bloco estático. Superfícies de risco incluem:
- inventários;
- dados NBT/componentes do block entity;
- capabilities/handlers de itens, fluidos ou energia;
- timers/processamento em andamento;
- links/referências por posição;
- listeners/redstone;
- estruturas de mods que não esperam mudança de coordenada.
A release fornece source/javadoc artifacts, mas esta auditoria não inventa uma allowlist/denylist interna sem inspeção byte/source específica de cada caso.
## 6. Redstone e movimento
Como provider de piston/movement, o sistema participa de atualização de vizinhos e mudanças espaciais. Mods que armazenam posições absolutas podem precisar reagir corretamente quando seu block entity é deslocado.
O servidor deve permanecer authority do movimento e do estado final. Clientes apenas representam a transição/resultado sincronizado.
## 7. Relação com MineColonies/Structurize
MineColonies continua authority de colônia, buildings, work orders e construção. Structurize fornece infraestrutura de templates/placement. Multi-Piston é um componente de movimento e não deve assumir ownership de colony data.
Uma construção MineColonies que use ou interaja com pistons precisa conservar:
- posição final coerente;
- block entity data;
- requests/work orders não corrompidos;
- nenhum bloco duplicado ou perdido após restart.
## 8. Client/server e lifecycle
Movimentação de blocos é estado de mundo server-authoritative. O cliente precisa receber o resultado e renderizar corretamente, mas não deve decidir o estado persistente.
Eventos críticos de lifecycle:
- ativação do piston;
- movimento/remoção temporária;
- recriação/reposicionamento de block entities;
- chunk save/unload;
- restart durante ou após operações;
- vizinhos atualizando após movimento.
## 9. Compatibilidade no pack
Pontos de atenção:
- block entities de Create e addons;
- máquinas com inventário/fluidos/energia;
- blocos MineColonies/Structurize;
- blocos decorativos parametrizados de Domum Ornamentum;
- mods de redstone/movimento.
Não há evidência documental de conflito geral com esses sistemas. Cada tipo de block entity precisa ser validado conforme comportamento real.
## 10. Riscos
1. **Block entity data loss** ao mover tipos não esperados.
2. **Duplication/desync** se origem e destino mantiverem estado simultaneamente.
3. **Capability invalidation** inadequada após mudança de posição.
4. **Chunk boundary:** movimento próximo a chunks descarregados precisa falhar/operar com segurança.
5. **Create overlap:** não misturar lifecycle de Multi-Piston com contraptions Create como se fossem o mesmo sistema.
6. **MineColonies dependency:** remoção/versão incompatível pode impedir boot do provider principal.
7. **Update regression:** a própria 1.2.58 altera movimentação de block entities.
## 11. Matriz de testes
- [ ] Dedicated server inicia com Multi-Piston 1.2.58 + MineColonies 1.1.1387.
- [ ] Piston multidirecional básico move blocos válidos em todas as direções suportadas pela build.
- [ ] Mover block entity com inventário preserva exatamente o conteúdo.
- [ ] Mover block entity com fluid/energy handler não duplica nem perde estado.
- [ ] Restart após movimento conserva posição/dados finais.
- [ ] Testar próximo a borda de chunk e com chunk vizinho descarregado.
- [ ] Testar bloco representativo de Create apenas se o sistema permitir movimento; falha deve ser segura.
- [ ] Testar contexto MineColonies/Structurize relevante sem corromper building/work order.
- [ ] Redstone/vizinhos atualizam uma vez e sem loop.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 12. Evidências e limites
- Modlist física: `multipiston-1.2.58-1.21.1.jar`, mod id/runtime exatos.
- CurseForge oficial: file ID 7097877, Release NeoForge 1.21/1.21.1, 12/10/2025.
- Changelog 1.2.58: `move some more blockentities`.
- A publicação disponibiliza artifacts de sources/javadocs da mesma release.
- MineColonies 1.1.1387: dependency mínima Multi-Piston 1.2.51, satisfeita pela build física.
- **Limite:** tipos exatos de block entity e internals de movimentação não foram inventados sem inspeção específica do source/JAR.
