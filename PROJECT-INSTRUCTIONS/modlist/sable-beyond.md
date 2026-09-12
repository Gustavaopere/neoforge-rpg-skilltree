# Sable Beyond

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811390ece5685708b632
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `sablebeyond-neoforge-1.21.1-v0.5.0.jar`, mod id `sable_beyond`, runtime `0.5.0`, mixins `sable_beyond.mixins.json` e `sable_beyond.neoforge.mixins.json`; Sable 2.0.5, Create 6.0.10, FlowingFluids 1.0.6 e Sable / Flowing Fluids Compat 1.0.2 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Sable Beyond 0.5.0 e o stack físico citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Sable Beyond
- **Arquivo JAR:** `sablebeyond-neoforge-1.21.1-v0.5.0.jar`
- **Versão 1.21.1:** 0.5.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Tecnologia
- **Função:** Expansão configurável do Sable com massa dinâmica/entity mass, forças de fluidos, fire updates e integrações Create como Encased Fan e Basin fluid intake/escaping.
- **Dependências:** Sable 2.0.5 é provider central. Create 6.0.10 é opcional no metadata 0.5.0 mas presente e ativa integrações Fan/Basin. FlowingFluids 1.0.6 e Sable / Flowing Fluids Compat 1.0.2 também estão presentes.
- **Sobreposição:** Complementa Sable e bridges específicos. Sobreposição parcial de força/fluido/massa deve ser testada para evitar double-processing.
- **Compatibilidade/Riscos:** Alpha intencional. Riscos: double-force com outras integrações de fluidos, double-count de massa, dupe/loss em Basin intake/escape, physics-tick leakage, config migration, provider API drift e unload races. 0.4.2 permanece fallback Release.
- **Observações:** 0.5.0 é Alpha e foi mantida deliberadamente pelas funções novas de fluidos/Create. A Release 0.4.2 é fallback estável, mas não cobre integralmente o delta funcional da 0.5.0.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Sable Beyond 0.5.0/0.4.x; deltas antigos usados apenas como lineage.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-beyond
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Sable Beyond 0.5.0 revalidado: Dynamic/Entity Mass lineage, FlowingFluid forces, fire config, Fan forces, Basin intake/escape, lifecycle, riscos e fallback 0.4.2.
- **Histórico da decisão:** 2026-09-06 — presença e v0.5.0 aprovadas como escolha intencional pelas melhorias de Sable/sublevels/fluidos. Pesquisa fechada; risco de Alpha preservado explicitamente.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `sablebeyond-neoforge-1.21.1-v0.5.0.jar`, mod id `sable_beyond`, versão `0.5.0`, NeoForge 1.21.1. A build instalada é **Alpha** e foi mantida intencionalmente porque acrescenta mecânicas úteis ao stack Sable/Create/FlowingFluids que a Release 0.4.2 não cobre integralmente.

## 1. Identidade, versão e maturidade
- **Mod:** Sable Beyond.
- **JAR:** `sablebeyond-neoforge-1.21.1-v0.5.0.jar`.
- **Mod id:** `sable_beyond`.
- **Versão:** `0.5.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Alpha.
- **Ambiente:** Client & Server.
- **Mixins físicos:** `sable_beyond.mixins.json` e `sable_beyond.neoforge.mixins.json`.
- **Fallback estável conhecido:** NeoForge 0.4.2 Release.

## 2. Papel e boundary
Sable Beyond adiciona features, QoL e compatibilidades que deliberadamente ficam fora do core Sable. Sable continua authority de sublevels/physics/transforms; Beyond acrescenta regras e integrações sobre essa infraestrutura.

Cada feature é configurável e algumas são desabilitadas por default. A simples presença do JAR não prova que Dynamic Mass, Entity Mass ou outras opções estejam ativas no pack.

## 3. Configuração
O projeto centraliza configs em `config/sable_beyond` e oferece tela de configuração in-game. A documentação também alerta que o projeto está em alpha e recomenda backup.

A linha histórica mudou paths/config e tornou sistemas como Dynamic Mass/Entity Mass opt-in. Portanto valores locais devem ser auditados antes de afirmar comportamento efetivo.

## 4. Dynamic Mass
A linha atual possui API/sistema de massa dinâmica. Histórico oficial registra implementação para Create fluid tanks, spouts, drains e basins, permitindo que conteúdo variável contribua à massa física.

Ownership permanece dividido: Sable calcula/simula o body; Beyond fornece massa dinâmica adicional a partir de state do provider. O valor nunca deve ser contabilizado duas vezes por outro compat.

## 5. Entity Mass
Beyond também possui sistema configurável de massa de entidades, podendo inclusive ser limitado a players conforme config histórica. A inicialização foi movida para depois de o servidor estar pronto em versão anterior para evitar crash.

Não presumir que entity mass está habilitada: o upstream documenta que esse tipo de feature pode vir desligado por default.

## 6. Flowing Fluids — delta 0.5.0
A 0.5.0 adiciona força de **Flowing Fluid** aplicada a sublevels e config dedicada para esse comportamento.

O pack contém FlowingFluids 1.0.6 e também `Sable / Flowing Fluids Compat 1.0.2`; são superfícies diferentes: Beyond aplica força de corrente ao body, enquanto o compat separado coordena ownership/entrada/saída de fluidos. Testar os dois juntos para evitar double-processing.

## 7. Fire update
A 0.5.0 adiciona configuração para fire update e mantém a linha de integração de fogo/lava em sublevels. A implementação histórica de fire mixins foi ajustada para compatibilidade com outros mods.

Fogo em estrutura móvel deve continuar obedecendo authority do servidor e não duplicar ticks apenas porque há physics ticks adicionais.

## 8. Encased Fan
No NeoForge, a 0.5.0 adiciona opção para configurar o multiplicador de força do airflow de Encased Fan sobre sublevels.

Create continua authority do fan/airflow original; Beyond traduz esse efeito para força física no Sable. Mudanças de multiplier precisam ser testadas contra massa/timestep reais do pack.

## 9. Basin Fluid Escaping
A 0.5.0 adiciona mecânica para **Basins invertidos** em sublevels liberarem fluido. O fluido que escapa também pode ser transferido para outros containers.

Regression gates: orientação do basin, quantidade conservada, recipient válido, unload/movimento durante transferência e ausência de dupe/loss.

## 10. Basin Fluid Intake
Basins voltados para cima podem absorver source blocks de fluido acima. Essa mecânica cruza world/sublevel ownership e deve liquidar cada source exatamente uma vez.

Testar especialmente com FlowingFluids, movimento/rotação e outros transports Create próximos.

## 11. Create como dependência opcional
A 0.5.0 tornou Create opcional no metadata NeoForge. Isso significa que Sable Beyond pode carregar sem Create, não que as features Create funcionem sem o provider.

No pack, Create 6.0.10 está presente, então Fan/Basin e outras integrações Create são superfícies reais.

## 12. Fixes removidos porque migraram de ownership
A 0.5.0 removeu o fix temporário de Mechanical Hand porque o suporte passou ao Sable base, e removeu um fix antigo de sprinkler de Slice & Dice.

Isso é importante para evitar dupla correção: Beyond não deve continuar interceptando caminhos cujo ownership já foi absorvido pelo provider.

## 13. Null safety e lifecycle
A build adiciona null checks quando um level fica inesperadamente indisponível após o servidor iniciar. Ainda assim, testar:
- server boot/shutdown;
- level/dimension unload;
- sublevel load/unload;
- restart com estruturas existentes;
- Create assembly/disassembly;
- fluid/fire updates durante unload.

## 14. Integrações concretas da modlist
- **Sable 2.0.5:** provider físico.
- **Create 6.0.10:** Fan/Basin e outras integrações.
- **FlowingFluids 1.0.6:** corrente/força da 0.5.0.
- **Sable / Flowing Fluids Compat 1.0.2:** ownership de fluido world↔sublevel.
- **Create Aeronautics 1.3.2:** aumenta a frequência de estruturas móveis/sublevels.

Essas integrações tornam a Alpha funcionalmente relevante e também ampliam sua superfície de regressão.

## 15. Maturidade e fallback
A versão 0.4.2 é a Release NeoForge estável disponível e corrige crash de EntityMass com Sable 2.0.1. A 0.5.0 é posterior, Alpha e contém as novas mecânicas de fluidos/fan/basins.

A decisão vigente **Manter** significa manter 0.5.0 conscientemente enquanto essas funções forem desejadas; não é afirmação de que Alpha é intrinsecamente superior. Downgrade exige avaliar perda funcional e compatibilidade com configs existentes.

## 16. Riscos técnicos
1. **Alpha regression:** código novo em fluid/fire/basins ainda menos maduro.
2. **Double force:** Beyond + outro mod aplicam força à mesma corrente/body.
3. **Mass double-count:** Dynamic Mass e outro compat contabilizam o mesmo conteúdo.
4. **Fluid dupe/loss:** Basin escaping/intake liquida volume incorretamente.
5. **Physics tick leakage:** lógica de gameplay roda por physics tick em vez de server tick.
6. **Config migration:** path/opção mudou entre versões e valor antigo é perdido.
7. **Provider drift:** Sable/Create/FlowingFluids mudam APIs/ownership.
8. **Unload race:** level/sublevel some durante callback.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Beyond 0.5.0 + Sable 2.0.5.
- [ ] Config screen/arquivos carregam sem recriar valores inesperadamente.
- [ ] Dynamic Mass, se habilitada, reage a fluid tank/basin sem double-count.
- [ ] FlowingFluids aplica força ao sublevel exatamente uma vez.
- [ ] Sable/Flowing Fluids Compat coexiste sem dupe/loss ou força duplicada.
- [ ] Encased Fan aplica força coerente com o multiplier configurado.
- [ ] Basin invertido libera fluido sem criar/perder volume.
- [ ] Basin voltado para cima absorve source válido exatamente uma vez.
- [ ] Movimento/rotação durante intake/escaping não quebra ownership.
- [ ] Fire update não multiplica ticks em estrutura móvel.
- [ ] Dimension/sublevel unload não gera null crash.
- [ ] Comparação controlada com 0.4.2 documenta o que seria perdido em eventual fallback.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física atual: Beyond 0.5.0, Sable 2.0.5, Create 6.0.10, FlowingFluids 1.0.6 e compat 1.0.2.
- CurseForge oficial: canal Alpha, configuração, alerta de backup e changelog exato 0.5.0.
- Histórico oficial 0.4.x/0.0.2 usado apenas como lineage de Dynamic Mass/Entity Mass/config migration.
- **Limite:** valores efetivos dos arquivos locais de config não foram lidos; features configuráveis não são marcadas como ativas sem essa evidência.
