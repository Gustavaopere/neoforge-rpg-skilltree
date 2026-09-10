# Create Diesel Generators

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8100beecc237240bf402
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Diesel Generators
- **Arquivo JAR:** `createdieselgenerators-1.21.1-1.3.15.jar`
- **Versão 1.21.1:** 1.21.1-1.3.15
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Adiciona motores diesel, petróleo bruto, refino, combustíveis e infraestrutura industrial para gerar potência cinética com combustíveis líquidos.
- **Dependências:** Create obrigatório; source matching declara Create 6.0.7–6.0.x e foi desenvolvido com Create 6.0.10. Pack físico usa Create 6.0.10. Integrações opcionais com Sable/Aeronautics exigem revalidação por version drift.
- **Sobreposição:** Overlap com outros sistemas industriais/petroquímicos deve ser comparado por fluid tags, refinery recipes e geração de energia. Não é redundância automática por compartilhar oil/diesel.
- **Compatibilidade/Riscos:** Riscos: fuel/fluid double-accounting; pumpjack state em chunk/restart; distillation scaling por heat sources; multiblock dupe/loss; fluid tag overlap; burner heat stale; contraption double-processing; Sable/Aeronautics drift.
- **Observações:** JAR `createdieselgenerators-1.21.1-1.3.15.jar`, mod id `createdieselgenerators`, runtime `1.21.1-1.3.15`. Source matching confirma Plant Oil, Crude Oil, Biodiesel, Diesel, Gasoline, Ethanol, concrete fluids e principais máquinas.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial 1.3.15 + source oficial george8188625/Create-Diesel-Generators branch 1.21.1 matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.21.1-1.3.15 com fluids, motores, pumpjack, distillation scaling, burner/heat, fermenter, canisters, contraption movement e overlaps industriais catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🛢️ **Identidade física e source matching confirmados:** `createdieselgenerators-1.21.1-1.3.15.jar`, mod id `createdieselgenerators`, runtime `1.21.1-1.3.15`. O source 1.21.1 declara exatamente essa versão e Create 6.0.10 como ambiente de desenvolvimento.

## 1. Papel e authority
Create Diesel Generators adiciona uma cadeia industrial baseada em **petróleo, refino, combustíveis líquidos e motores** integrada à cinética Create. O addon owns fluid IDs, máquinas, recipes e fuel semantics próprios; Create owns RPM/stress e a infraestrutura cinética base.

## 2. Fluidos registrados confirmados
O source matching registra **Plant Oil, Crude Oil, Biodiesel, Diesel, Gasoline e Ethanol**, todos com source/flowing fluid e bucket. Também registra fluidos de concreto/cimento por cada DyeColor. IDs e propriedades efetivas devem vir do registry/runtime matching.

## 3. Motores
O source confirma **Diesel Engine, Modular Diesel Engine e Huge Diesel Engine**, além de Powered Engine Shaft. Geração cinética precisa consumir combustível exatamente uma vez e refletir corretamente start/stop, fuel exhaustion e overstress.

## 4. Pumpjack
A linha contém **Pumpjack Bearing, Pumpjack Head, Pumpjack Hole, Pumpjack Crank** e movement behaviours próprios. Extração de crude oil é um sistema stateful: chunk unload, contraption movement e restart não podem duplicar fluido ou desacoplar componentes.

## 5. Distillation Tank
O **Distillation Tank** é parte da cadeia de refino. A release 1.3.15 altera o scaling: distillers maiores processam mais quando possuem mais fontes de calor. Portanto tamanho, heat-source detection e throughput são regression gates específicos desta build.

## 6. Burner e heat integration
O source matching registra o Burner como `BoilerHeater` do Create. Seu estado lit/heat participa de sistemas Create que consultam fonte de calor. Mudança de combustível ou bloco não pode deixar heat state stale.

## 7. Bulk Fermenter
O **Bulk Fermenter** aparece no source matching como multiblock próprio, ligado à cadeia de biocombustíveis/fermentação. Inventory/fluid state e formação/desformação do multiblock devem ser conservativos em unload/restart.

## 8. Canister e fluid storage
O addon registra Canister com item próprio e storage de fluido. Transferência bucket↔canister↔tank/pipe precisa preservar quantidade e tipo, inclusive após break/replacement e automação por capabilities.

## 9. Concrete fluids
Há uma variante de concrete/cement fluid para cada cor vanilla. Esses fluidos têm buckets e comportamento de dispenser registrado. Não confundir seus IDs com concrete blocks de outros mods; interoperabilidade só existe por tags/recipes explícitos.

## 10. Chemical Turret e outros blocos industriais
O source registra Chemical Turret com impacto de stress, além de Basin Lid, Oil Barrel, concrete-encased pipes, sheet-metal/estrutura e outros componentes. O dossier não fixa listas/valores além do source inspecionado quando a implementação completa não foi aberta.

## 11. Contraption movement
Diesel engines e partes do pumpjack possuem MovementBehaviour registrados. Ao entrar em contraption, transform e machine state precisam seguir a semântica do addon sem duplicar geração ou continuar processando em duas representações simultâneas.

## 12. Create compatibility range
O source exato declara Create na faixa 6.0.7 até antes de 6.1 e foi desenvolvido com Create 6.0.10. O pack usa Create 6.0.10, portanto a dependência principal está alinhada diretamente.

## 13. Sable/Aeronautics ecosystem
O source inclui dependências de desenvolvimento para Sable/Aeronautics/Offroad/Simulated em revisões antigas. O pack usa Sable 2.0.5 e Aeronautics 1.3.2; isso prova superfície de integração upstream, mas o version drift exige smoke-test e não autoriza inferir comportamento físico específico.

## 14. Delta 1.3.15 — Andesite Girder Struts
A 1.3.15 adiciona **Andesite Girder Struts** como compat com Strut Your Stuff. Se o provider externo não estiver presente, não tratar essa compat como runtime ativa; o conteúdo condicional deve falhar sem classloading obrigatório indevido.

## 15. Sobreposição de petróleo
O pack possui outros sistemas industriais/petroquímicos. O overlap relevante é por **fluid tags, recipes, refinery chains e geração de energia**, não por nome. Create: Addon Compatibility ou datapacks podem unificar rotas, mas o provider original continua authority de seus fluids/machines.

## 16. Client/server e multiplayer
Extração, fluid accounting, processing, engine output, multiblock state e inventories são server-authoritative. Models, particles e gauges são client-facing. Dois clientes não podem acionar o mesmo processamento/transfer duas vezes.

## 17. Lifecycle
Testar pumpjack, distillation, fermenter, engines, canisters, burners e concrete fluids em place/break, chunk unload, restart, `/reload`, contraption assembly e fuel depletion. Multiblocks parcialmente carregados são caso crítico.

## 18. Riscos
1. Fuel consumption duplica ou não ocorre enquanto há geração.
2. Pumpjack produz crude oil novamente após reload indevido.
3. Distillation scaling 1.3.15 conta fontes de calor erradas.
4. Multiblock fermenter/tank duplica fluid/item ao reformar.
5. Canister perde data ou converte fluido incorretamente.
6. Tags de diesel/oil colidem semanticamente com outro addon.
7. Burner mantém heat stale.
8. MovementBehaviour produz engine ativa no bloco e na contraption simultaneamente.
9. Sable/Aeronautics API drift quebra movimento/physics integration.
10. Concrete fluid de cor colide com recipe/tag externo.
11. Compat opcional causa classloading sem provider.

## 19. Matriz de testes
- [ ] Dedicated server inicia com CDG 1.3.15 + Create 6.0.10.
- [ ] Pumpjack extrai e persiste estado sem dupe após chunk/restart.
- [ ] Distillation Tank escala throughput com heat sources conforme 1.3.15.
- [ ] Diesel/Modular/Huge engines consomem combustível uma vez e param corretamente.
- [ ] Burner atualiza heat/boiler state sem cache stale.
- [ ] Bulk Fermenter forma/desforma sem perda/dupe.
- [ ] Canister conserva fluido em transfers e break/relog.
- [ ] Crude Oil/Diesel/Gasoline/Biodiesel/Ethanol/Plant Oil resolvem IDs corretos.
- [ ] Concrete fluids/buckets funcionam em dispenser e reload.
- [ ] Contraption assembly não duplica processamento/geração.
- [ ] Sable 2.0.5/Aeronautics 1.3.2 não quebram hooks opcionais.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidências e limites
A modlist física e o source exato confirmam runtime 1.21.1-1.3.15. O source confirma os principais motores, pumpjack, distillation, burner, fermenter, canister e fluid registry. O changelog oficial confirma Andesite Girder Struts e scaling de distillers. Config local, fuel values e throughput numérico não foram inventados.

> 🔒 **Boundary canônico:** CDG owns petróleo/refino/fuel semantics; Create owns cinética. Toda conversão item/fluid→processamento→stress deve conservar recursos exatamente uma vez.
