# Create Big Cannons

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81558e59dce79993a0ca
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Big Cannons
- **Arquivo JAR:** `createbigcannons-5.11.7+mc.1.21.1.jar`
- **Versão 1.21.1:** 5.11.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, RPG
- **Função:** Sistema completo de artilharia do Create, com construção, carregamento e operação de grandes canhões/autocannons e munições.
- **Dependências:** Pack físico: Create 6.0.10 + Ritchie's Projectile Library 2.1.2. Integrações concretas: Sable 2.0.5, Create Aeronautics 1.3.2, Harness 1.0.1, CBC Advanced Technology 0.1.4c e CBCAT Fix 1.0.1 filename/runtime 1.0.0.
- **Sobreposição:** Tema bélico se cruza com Gunsmithing/mísseis, mas CBC owns artilharia pesada/projectiles. Addons CBC e Harness são consumers/integrations; não substituem o mod-base.
- **Compatibilidade/Riscos:** Riscos: RPL/Sable drift; shell-impact NoSuchMethodError regression; recoil duplicado em physics objects; data legado de munition/ammo containers; Schematic Printer consumption; CBC AT/CBCAT Fix API drift; projectile chunk/multiplayer desync.
- **Observações:** JAR `createbigcannons-5.11.7+mc.1.21.1.jar`, mod id `createbigcannons`, runtime 5.11.7. 5.11.7 adiciona suporte Sable 2.0 e corrige Schematic Printer/shell-impact compat; linha 5.11.4 migrou data de munitions/containers.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + releases/changelogs oficiais CBC 5.11.7/5.11.x + RPL 2.1.2 e integrações físicas confirmadas.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-big-cannons
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 5.11.7 com big cannons/autocannons, fabricação, munition data, RPL 2.1.2, Sable recoil/impact, 5.11.4 migration e addon boundaries catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 💣 **Identidade física confirmada:** `createbigcannons-5.11.7+mc.1.21.1.jar`, mod id `createbigcannons`, runtime `5.11.7`. O pack também contém Ritchie's Projectile Library `2.1.2`, Create 6.0.10, Sable 2.0.5 e a cadeia de addons CBC relevante.

## 1. Papel e authority
Create Big Cannons (CBC) é o mod-base de artilharia pesada do ecossistema Create. Ele owns materiais/estruturas de canhão, autocannons, mounts, carregamento, propellant, munições, fuzes, projectile behavior e processos de fabricação próprios. Create continua owner da cinética/contraptions; Ritchie's Projectile Library fornece infraestrutura de projéteis usada pela linha atual.

## 2. Dependência física de projéteis
A modlist atual contém `ritchiesprojectilelib-2.1.2-mc.1.21.1-neoforge.jar`, mod id `ritchiesprojectilelib`, runtime 2.1.2. Essa dependência é concreta; não deve ser removida ou versionada isoladamente sem validar CBC e outros consumers.

## 3. Big cannons
CBC permite construir grandes canhões a partir de componentes de barrel/chamber/material. Assembly e integridade do canhão são state funcional do provider; scripts externos não devem tratar uma fileira de blocks como canhão válido sem a validação interna.
Material, bore e limites reais permanecem sob data/config/runtime da build.

## 4. Autocannons
A linha também possui autocannons e ammo handling próprios, com cadência/carregamento distintos dos big cannons. Não agrupar ambos como uma única recipe/munição genérica em integrações.

## 5. Mounts e aiming
CBC possui mounts/control surfaces para posicionar e mirar canhões. Rotação, target e recoil devem convergir no servidor; render interpolado não é authority da orientação de disparo.
Quando um mount participa de contraption/physics object, ownership de transform precisa ficar claro entre CBC, Create e Sable/Aeronautics.

## 6. Cannon Casting, Drill e Builder
A cadeia de fabricação inclui Cannon Casting e ferramentas/máquinas como Cannon Drill e Cannon Builder. Esses processos controlam formação/usinagem de componentes e devem executar exatamente uma vez por input/estrutura válida.
Recipes e data carregados são authority para material/output; não inventar tempos ou stress sem config matching.

## 7. Munições e projectile blocks
CBC trabalha com munições em forma de items/blocks e projéteis disparados. State como fuzing, tracer, carga e conteúdo precisa sobreviver ao caminho inventory→loading→cannon→projectile sem duplicação/perda.

## 8. Fuzes e tracers
Fuzes e tracers alteram comportamento do projectile. O servidor deve decidir detonação/impacto; efeitos visuais são client-facing. Copiar item data de maneira incompleta pode criar munição visualmente correta mas semanticamente inválida.

## 9. Propellant e cartridges
Propellant/cartridges participam do cálculo e segurança do disparo. Consumo deve ocorrer uma única vez; excesso/combinação inválida precisa seguir a política do mod, não uma regra genérica de Create.

## 10. Ammo containers
Containers de munição possuem state próprio e são superfícies de inventory/persistence. Inserção, extração, break e reload não podem duplicar munição nem esquecer data associada aos stacks.

## 11. Migração importante da linha 5.11.x
A 5.11.4 alterou o armazenamento de dados de **big cannon munition blocks** e **autocannon ammo container blocks** na 1.21.1. O upstream alertou que blocos existentes podiam quebrar após a mudança.
Mundos que atravessaram essa versão precisam de smoke-test de munitions/containers antigos; a 5.11.7 não autoriza presumir migração perfeita de todo state legado.

## 12. Schematic Printer — fix 5.11.7
A 5.11.7 corrige projectile block items sem fuze/non-tracer que não podiam ser consumidos pelo Schematic Printer. Esse fluxo é regression gate específico quando schematics CBC fazem parte de uma build automatizada.
Consumo pelo printer deve ocorrer uma vez e preservar/validar os data components relevantes.

## 13. Sable 2.0 — suporte 5.11.7
O changelog 5.11.7 adiciona suporte à linha **Sable 2.0.0** e corrige um `NoSuchMethodError` na compatibilidade de impacto de shells.
O pack usa Sable 2.0.5, portanto a superfície é diretamente ativa e deve ser testada com impactos/recoil em physics objects.

## 14. Recoil em physics objects
A linha recente integra recoil com objetos físicos Sable. O recoil deve ser aplicado uma vez ao body correto, com direction/transform coerentes.
Outros addons que também aplicam força ao mesmo cannon/contraption não devem reproduzir o recoil de CBC.

## 15. Integrações físicas do pack
Estão presentes Create Aeronautics 1.3.2, Sable 2.0.5, Copycats+ 3.0.9 e Create Aeronautics: Harness 1.0.1. O Harness 1.0.1 declara compatibilidade CBC para canhões carregados nas costas.
Testar disparo CBC em harness/physics object como integração concreta, principalmente constraint + recoil + projectile spawn.

## 16. CBC Advanced Technology e CBCAT Fix
O pack contém `cbc_at_Neoforge_1.21.1_0.1.4c.jar` e `cbcatfix-1.21.1-neoforge-1.0.1.jar`. São consumers/patches do domínio CBC e precisam permanecer alinhados à API/data da base.
Esta ficha não assume que CBCAT Fix ficou obsoleto só porque CBC 5.11.7 avançou; a necessidade é auditada na própria página/posição.

## 17. Create Aero Radar e outros consumers
Create Aero Radar e outras integrações do pack podem detectar/usar entidades ou equipamentos CBC. CBC continua authority da identidade e trajetória do projectile; radar/GUI não deve alterar state de munição ou impacto.

## 18. Data-driven materials
A 5.11.7 adiciona exemplos de JSON para materiais de big cannon e autocannon em `example_createbigcannons`. Isso evidencia uma superfície data-driven para materiais.
Datapacks podem ampliar/alterar materiais; `/reload` precisa produzir registry/data coerente e não deixar cannon existente em state impossível sem fallback seguro.

## 19. Client/server e multiplayer
Projectile spawn, trajectory authority, collision, damage/explosion, inventory consumption, cannon assembly e recoil são server-authoritative. Models, particles, sound e interpolation são client-facing.
Dois clientes não podem causar disparo duplo do mesmo trigger nem divergir sobre projectile state.

## 20. Chunk/restart lifecycle
Canhões, mounts e ammo containers persistem em chunks enquanto projectiles podem atravessar limites rapidamente. Testar unload/reload, restart com cannon carregado, projectile atravessando chunk boundary e physics object parcialmente carregado.

## 21. Riscos
1. Ritchie’s Projectile Library drift quebra spawn/collision.
2. Sable shell-impact compat reintroduz `NoSuchMethodError`.
3. Recoil é aplicado duas vezes em physics object.
4. Harness constraint + recoil gera impulso/teleporte inválido.
5. Munition/container data legado de 5.11.4 fica corrompido.
6. Schematic Printer não consome ou duplica projectile block item.
7. Fuze/tracer data é perdido no loading.
8. Ammo container duplica stacks em break/reload.
9. Cannon assembly aceita geometria/material inválido após datapack reload.
10. CBCAT/addons esperam API/data anterior.
11. Projectile cruza chunk/unload e perde collision/detonação.
12. Dois clientes acionam o mesmo disparo duas vezes.

## 22. Matriz de testes
- [ ] Dedicated server inicia com CBC 5.11.7 + Create 6.0.10 + RPL 2.1.2.
- [ ] Big cannon build/load/fire executa uma única vez.
- [ ] Autocannon alimenta e consome munição corretamente.
- [ ] Cannon Casting/Drill/Builder processam sem dupe/loss.
- [ ] Fuze/tracer state sobrevive inventory→loading→projectile.
- [ ] Ammo container persiste conteúdo em restart/break controlado.
- [ ] Blocos antigos da migração 5.11.4 são validados em cópia de mundo quando aplicável.
- [ ] Schematic Printer consome munição não-fuzed/non-tracer conforme fix 5.11.7.
- [ ] Shell impact em Sable 2.0.5 não reproduz o `NoSuchMethodError` corrigido.
- [ ] Recoil em physics object aplica força uma vez.
- [ ] Harness 1.0.1 + CBC dispara sem constraint/recoil duplicados.
- [ ] CBC AT/CBCAT Fix continuam carregando e usando a base sem crash.
- [ ] Projectile atravessa chunks/multiplayer sem desync de impacto.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 23. Evidências e limites
A modlist física confirma CBC 5.11.7 e RPL 2.1.2. Publicações oficiais da 5.11.7 confirmam NeoForge 1.21.1, Client & Server, suporte Sable 2.0, exemplos de material JSON e fixes de Schematic Printer/shell impact. Histórico oficial 5.11.x confirma a migração de data de munitions/containers e recoil Sable. Valores balísticos, listas completas de materiais/munições e configs não foram inventados.

> 🔒 **Boundary canônico:** CBC owns cannon/munition/projectile/recoil semantics; Create owns kinetics; RPL fornece a infraestrutura projectile; Sable owns physics bodies. Cada disparo, consumo e recoil deve ser liquidado exatamente uma vez no servidor.
