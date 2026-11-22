# Create: Diesel Generators — 1.21.1-1.3.16 (instalação declarada em 22/11/2026)

> **Vigente em 22/11/2026:** `1.21.1-1.3.15` → **`1.21.1-1.3.16`**; JAR oficial publicado `createdieselgenerators-1.21.1-1.3.16.jar`, [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators/files/9739370). Instalação confirmada pela declaração do usuário, não por leitura do JAR local ou metadados da nova build.

> **Auditoria das publicações intermediárias:** 1.3.16 (21/11/2026) corrige throttle que não atualizava stress capacity, spam do indexador JEI e crash ao tingir Copycats; implementa multiplicador de fuel burn por turbocharging, impõe limite de boiler heating por burner e permite comprimento máximo configurável para oil barrels. A 1.3.15 histórica documenta distillation/heat scaling e Andesite Girder Struts.

> **Dependências aguardando sincronização documental:** Create 6.0.x na fonte da versão anterior; consumidores de fluid tags e integração com Sable/Aeronautics/Copycats precisam conciliação documental posterior se divergentes, sem bloquear esta atualização.

> **Riscos/limites de evidência:** Testar conservação de petróleo/combustível, stress capacity, turbo fuel rate, boiler limit, barrel max length e contraptions; não atribuir valores numéricos não publicados.

> **Snapshot físico histórico (posição #181):** createdieselgenerators-1.21.1-1.3.15.jar, 1.21.1-1.3.15; dados referentes à modlist física anterior à declaração de atualização.


> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#181**: JAR `createdieselgenerators-1.21.1-1.3.15.jar`, mod id `createdieselgenerators`, runtime `1.21.1-1.3.15`, SHA-1 `7f751dabfa83c141c1d13025cd16f06553ba459b`.

## Propriedades do registro

- **Mod:** Create Diesel Generators
- **Arquivo JAR instalado (declarado pelo usuário):** `createdieselgenerators-1.21.1-1.3.16.jar` — [distribuição](https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators/files/9739370); local não relido.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.21.1-1.3.16` (antes `1.21.1-1.3.15`; metadata do novo JAR não lido).
- **Categoria:** Tecnologia, Automação
- **Função:** Adiciona motores diesel, petróleo bruto, refino, combustíveis e infraestrutura industrial para gerar potência cinética com combustíveis líquidos.
- **Dependências:** Create 6.0.x na fonte da versão anterior; consumidores de fluid tags e integração com Sable/Aeronautics/Copycats precisam conciliação documental posterior se divergentes, sem bloquear esta atualização.
- **Estado no pack:** `1.21.1-1.3.16` declarado instalado pelo usuário em 22/11/2026; inventário documental sincronizado; sem inspeção local.
- **Estado da pesquisa:** versão-alvo e distribuição verificadas; lançamentos intermediários documentados no dossier; lacunas e limites declarados no cabeçalho.
- **Compatibilidade/Riscos:** Testar conservação de petróleo/combustível, stress capacity, turbo fuel rate, boiler limit, barrel max length e contraptions; não atribuir valores numéricos não publicados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators
- **Procedência:** posição física histórica #181; declaração de instalação em 22/11/2026 e publicação oficial [CurseForge](https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators/files/9739370); nenhum JAR local atual reinspecionado.
- **Observações vigentes:** distribuição `createdieselgenerators-1.21.1-1.3.16.jar`; versão `1.21.1-1.3.16` declarada instalada, sem metadata/SHA local. Dados anteriores na documentação detalhada são históricos.
- **Atualização/Status:** 22/11/2026 — `1.21.1-1.3.15` → `1.21.1-1.3.16` documentado. 1.3.16 (21/11/2026) corrige throttle que não atualizava stress capacity, spam do indexador JEI e crash ao tingir Copycats; implementa multiplicador de fuel burn por turbocharging, impõe limite de boiler heating por burner e permite comprimento máximo configurável para oil barrels. A 1.3.15 histórica documenta distillation/heat scaling e Andesite Girder Struts.
- **Decisão:** Sem decisão
- **Sobreposição:** Overlap com outros sistemas industriais/petroquímicos deve ser comparado por fluid tags, refinery recipes e geração de energia. Não é redundância automática por compartilhar oil/diesel.

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

## Auditoria incremental upstream — Create: Diesel Generators 1.3.16 (22/11/2026; não instalada)

Instalado fisicamente: #181 `createdieselgenerators-1.21.1-1.3.15.jar`, versão `1.21.1-1.3.15`. A [**1.3.16**, CurseForge file 9739370](https://www.curseforge.com/minecraft/mc-mods/create-diesel-generators/files/9739370), publicada em **21/11/2026**, é Release para **NeoForge 1.21.1**, artefato `createdieselgenerators-1.21.1-1.3.16.jar`. Todas as notas específicas da publicação oficial:
- Corrige **engine throttle** que não alterava a **stress capacity**; afeta diretamente cinética/Create, capacidade disponível e balanceamento de motores.
- Corrige **log spam durante criação do índice JEI**; relevante ao startup e reload.
- Corrige **crash ao tingir Copycats**.
- Implementa **multiplicador da taxa de queima por turbocharging**, relevante a consumo de combustível e eficiência.
- Impõe **limite à capacidade de aquecimento de boilers por burner**, relevante à escala de produção de steam/stress.
- Adiciona **comprimento máximo configurável de oil barrels**, [PR #382](https://github.com/GeorgeVI/Create-Diesel-Generators/pull/382). A publicação também cita limpeza de código, sem detalhar mudança de contrato.

**QA:** comparar stress/torque e taxa de burn com throttle/turbo, boiler heating caps, cadeia oil/diesel/canisters, refino, oil barrels configurados, Copycats, JEI, save/restart, contraptions Create/Sable e multiplayer server-authoritative; verificar os defaults no próprio JAR, não inventar valores. A fonte não fornece ranges de compatibilidade do perfil com os demais addons. **Nenhum JAR ou config alterado nem gameplay testado**. Categoria e versão física preservadas.
